//package unit.kafka.producer;
//
//import com.epam.kafka.producer.ResourceIdProducer;
//import com.epam.service.ResourceService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.logging.Logger;
//
//import static org.mockito.Mockito.verify;
//
//public class ResourceIdProducerTest {
//
//    @Mock
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Mock
//    private Logger logger;
//
//    @InjectMocks
//    private ResourceIdProducer resourceIdProducer;
//
//    private final String topicName = "test-topic";
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(resourceIdProducer, "topicName", topicName);
//    }
//
////    @Test
////    public void testSendResourceId() {
////        Integer resourceId = 123;
////
////        resourceIdProducer.sendResourceId(resourceId);
////
////        verify(kafkaTemplate).send(topicName, String.valueOf(resourceId));
////    }
//}
