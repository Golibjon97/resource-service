package com.epam.service;

import com.epam.config.properties.AwsS3BucketProperties;
import com.epam.entity.Mp3File;
import com.epam.exception.MetadataNotFoundException;
import com.epam.exception.ResourceNotFoundException;
import com.epam.kafka.producer.ResourceIdProducer;
import com.epam.repository.ResourceLocationRepository;
import io.awspring.cloud.s3.S3Template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
@EnableConfigurationProperties(AwsS3BucketProperties.class)
public class ResourceService {

    private final ResourceLocationRepository resourceLocationRepository;
    private final S3Template s3Template;
    private final ResourceIdProducer resourceIdProducer;
    private final StagingService stagingService;

    private static final Integer STAGING_ID = 1;
    private static final Integer PERMANENT_ID = 2;

    @Autowired
    public ResourceService(ResourceLocationRepository resourceLocationRepository,
                           S3Template s3Template,
                           ResourceIdProducer resourceIdProducer,
                           StagingService stagingService) {
        this.resourceLocationRepository = resourceLocationRepository;
        this.s3Template = s3Template;
        this.resourceIdProducer = resourceIdProducer;
        this.stagingService = stagingService;
    }

    public Integer saveFile(byte[] mp3Data) {
        Mp3File mp3File = getMp3Object(mp3Data);

        Integer savedFileId = stagingService.toStaging(mp3File);

        resourceIdProducer.sendResourceId(savedFileId, mp3File);

        return savedFileId;
    }

    public byte[] getMp3File(Integer resourceId, String userAgent) throws IOException {
        log.info("Fetching mp3Byte with id {} and userAgent {}", resourceId, userAgent);
        String s3LocationObject = resourceLocationRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + resourceId + " not found"))
                .getObject();

        Integer bucketStatus = userAgent.contains("Java") ? STAGING_ID : PERMANENT_ID;
        String bucketName = stagingService.getStorage(bucketStatus).getBucket();

        try (InputStream is = s3Template.download(bucketName, s3LocationObject).getInputStream()) {
            return IOUtils.toByteArray(is);
        }
    }

    public void deleteFiles(String ids) {
        log.info("Deleting files with ids: {}", ids);
        var bucketName = stagingService.getStorage(PERMANENT_ID).getBucket();
        Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .map(resourceLocationRepository::getReferenceById)
                .filter(Objects::nonNull)
                .forEach(s3Location -> {
                    s3Template.deleteObject(bucketName, s3Location.getObject());
                    resourceIdProducer.deleteResourceId(s3Location.getId());
                    resourceLocationRepository.deleteById(s3Location.getId());
                });
    }

    private Mp3File getMp3Object(byte[] mp3Data) {
        Mp3Parser mp3Parser = new Mp3Parser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        Mp3File mp3File;

        try {
            mp3Parser.parse(new ByteArrayInputStream(mp3Data), handler, metadata, context);
            String title = metadata.get("dc:title");
            if (title == null || title.isEmpty()) {
                throw new MetadataNotFoundException("MP3 metadata title not found");
            }
            mp3File = new Mp3File();
            mp3File.setData(mp3Data);
            mp3File.setName(title);
        } catch (TikaException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        return mp3File;
    }

}