package com.epam.kafka.producer;

import com.epam.entity.Mp3File;
import com.epam.service.StagingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ResourceIdProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;
    private final StagingService stagingService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ResourceIdProducer(KafkaTemplate<String, String> kafkaTemplate, StagingService stagingService) {
        this.kafkaTemplate = kafkaTemplate;
        this.stagingService = stagingService;
    }

    public void sendResourceId(Integer resourceId, Mp3File mp3File) {
        log.info("Resource Id sent {}", resourceId);
        ProducerRecord<String, String> record =
                setTraceId(topicName, String.valueOf(resourceId));

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                throw new KafkaException(ex.getMessage());
            } else {
                log.info("Sending mp3File to Permanent");
                stagingService.toPermanent(resourceId, mp3File);
            }
        });
    }

    public void deleteResourceId(Integer resourceId) {
        log.info("Deletion {}", resourceId);
        ProducerRecord<String, String> record =
                setTraceId("resource_id_del", String.valueOf(resourceId));

        kafkaTemplate.send(record);
    }

    private ProducerRecord<String, String> setTraceId(String topicName, String resourceId) {
        String traceId = MDC.get("trace-id");
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, String.valueOf(resourceId));
        record.headers().add(new RecordHeader("trace-id", traceId.getBytes()));

        return record;
    }

}
