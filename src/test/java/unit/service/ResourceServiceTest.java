//package unit.service;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.epam.config.properties.AwsS3BucketProperties;
//import com.epam.entity.Mp3File;
//import com.epam.entity.S3Location;
//import com.epam.kafka.producer.ResourceIdProducer;
//import com.epam.repository.ResourceLocationRepository;
//import com.epam.service.ResourceService;
//import io.awspring.cloud.s3.S3Resource;
//import io.awspring.cloud.s3.S3Template;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//
//import org.apache.tika.exception.TikaException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.xml.sax.SAXException;
//import unit.objectmothers.ObjectMother;
//
//@ExtendWith(MockitoExtension.class)
//public class ResourceServiceTest {
//
//  @InjectMocks
//  private ResourceService resourceService;
//
//  @Mock
//  private ResourceIdProducer resourceIdProducer;
//
//  @Mock
//  private ResourceLocationRepository resourceLocationRepository;
//
//  @Mock
//  private AwsS3BucketProperties awsS3BucketProperties;
//
//  @Mock
//  private S3Template s3Template;
//
//  @Mock
//  private S3Resource s3Resource;
//
//  @Mock
//  private S3Location s3Location;
//
////  @Test
////  void saveFileTest() throws IOException, TikaException, SAXException {
////    byte[] mp3Data = ObjectMother.mp3Data();
////    String mockTitleName = "Tu principe";
////    Integer expectedFileId = 1;
////
////    ResourceService resourceServiceSpy = spy(resourceService);
////
////    doReturn(mockTitleName).when(resourceServiceSpy).getMetadataName(mp3Data);
////    S3Location mockS3Location = new S3Location();
////    mockS3Location.setId(expectedFileId);
////    doReturn(mockS3Location).when(resourceServiceSpy).saveFileS3(any(Mp3File.class));
////
////    Integer actualFileId = resourceServiceSpy.saveFile(mp3Data);
////
////    assertEquals(expectedFileId, actualFileId);
////    verify(resourceServiceSpy).getMetadataName(mp3Data);
////    verify(resourceServiceSpy).saveFileS3(any(Mp3File.class));
////    verify(resourceIdProducer).sendResourceId(expectedFileId);
////
////  }
//
////  @Test
////  void getMp3FileTest() throws IOException{
////    // Arrange
////    Integer resourceId = 1;
////    String fileName = "song.mp3";
////    String bucketName = "test-bucket";
////    byte[] expectedBytes = "mp3 file content".getBytes();
////
////    when(resourceLocationRepository.getReferenceById(resourceId)).thenReturn(s3Location);
////    when(s3Location.getObject()).thenReturn(fileName);
////    when(awsS3BucketProperties.getBucketName()).thenReturn(bucketName);
////    when(s3Template.download(bucketName, fileName)).thenReturn(s3Resource);
////    when(s3Resource.getInputStream()).thenReturn(new ByteArrayInputStream(expectedBytes));
////
////    // Act
//////    byte[] actualBytes = resourceService.getMp3File(resourceId);
////
////    // Assert
////    assertThat(actualBytes).isEqualTo(expectedBytes);
////    verify(resourceLocationRepository).getReferenceById(resourceId);
////    verify(s3Template).download(bucketName, fileName);
////
////  }
//
//  @Test
//  void testDeleteFiles() {
//
//    String ids = "1,2,3";
//    String bucketName = ObjectMother.s3Location().getBucket();
//    S3Location location1 = ObjectMother.s3Location();
//
//    when(awsS3BucketProperties.getBucketName()).thenReturn(bucketName);
//    when(resourceLocationRepository.getReferenceById(1)).thenReturn(location1);
//
//    resourceService.deleteFiles(ids);
//
//    verify(s3Template).deleteObject(bucketName, ObjectMother.s3Location().getObject());
//  }
//
//}
