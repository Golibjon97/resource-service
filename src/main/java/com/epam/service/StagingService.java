package com.epam.service;

import com.epam.entity.Mp3File;
import com.epam.entity.S3Location;
import com.epam.entity.Storage;
import com.epam.mapper.S3LocationMapper;
import com.epam.repository.ResourceLocationRepository;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@Slf4j
public class StagingService {

    private final ResourceLocationRepository resourceLocationRepository;
    private final S3Template s3Template;
    private final S3LocationMapper s3LocationMapper;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
//    private final FallbackService fallbackService;

    private static final Integer STAGING_ID = 1;
    private static final Integer PERMANENT_ID = 2;

    @Autowired
    public StagingService(ResourceLocationRepository resourceLocationRepository,
                          S3Template s3Template,
                          S3LocationMapper s3LocationMapper,
                          RestTemplate restTemplate,
//                          FallbackService fallbackService,
                          DiscoveryClient discoveryClient) {
        this.resourceLocationRepository = resourceLocationRepository;
        this.s3Template = s3Template;
        this.s3LocationMapper = s3LocationMapper;
        this.restTemplate = restTemplate;
//        this.fallbackService = fallbackService;
        this.discoveryClient = discoveryClient;
    }

    public Integer toStaging(Mp3File mp3File) {
        Storage storageStaging = getStorage(STAGING_ID);
        S3Resource s3Resource = saveFileS3(mp3File, storageStaging.getBucket());
        S3Location s3LocationSaved = s3LocationMapper.toS3Location(s3Resource);
        resourceLocationRepository.save(s3LocationSaved);
        return s3LocationSaved.getId();
    }

    public void toPermanent(Integer savedFileId, Mp3File mp3File) {
        String permanentBucketName = getStorage(PERMANENT_ID).getBucket();
        String stagingBucketName = getStorage(STAGING_ID).getBucket();

        saveFileS3(mp3File, permanentBucketName);
        s3Template.deleteObject(stagingBucketName, mp3File.getName());
        resourceLocationRepository.updateToPermanent(savedFileId);
    }

    @CircuitBreaker(name = "storageService", fallbackMethod = "fallback")
    public Storage getStorage(Integer id) {
        log.info("Going to storage service");

        String uri = discoveryClient
                .getInstances("spring-cloud-gateway")
                .get(0)
                .getUri()
                .toString();
        uri += "/api/v1/storages/" + id;

        return restTemplate.getForEntity(uri, Storage.class).getBody();
    }

    private Storage fallback(Integer id, Exception ex) {

        log.error("Storage Service is unavailable. Returning stub data for storage ID: {}", id, ex);

        Storage stubStorage = new Storage();
        stubStorage.setId(id);
        if (id == 1) {
            stubStorage.setBucket("resource-storage-service-staging");
            stubStorage.setStorageType("STAGING");
            stubStorage.setPath(
                    "https://us-east-1.console.aws.amazon.com/s3/buckets/resource-storage-service-staging?region=us-east-1&bucketType=general&tab=objects"
            );
        } else {
            stubStorage.setBucket("resource-storage-service-permanent");
            stubStorage.setStorageType("PERMANENT");
            stubStorage.setPath(
                    "https://us-east-1.console.aws.amazon.com/s3/buckets/resource-storage-service-permanent?region=us-east-1&bucketType=general&tab=objects"
            );
        }

        return stubStorage;
    }

    private S3Resource saveFileS3(Mp3File mp3File, String bucketName) {
        String traceId = MDC.get("trace-id");
        log.info("saving file to S3 with Trace-id: {}", traceId);
        InputStream inputStream = new ByteArrayInputStream(mp3File.getData());
        return s3Template.upload(bucketName, mp3File.getName(), inputStream);
    }

}
