//package unit.mapper;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import com.epam.config.properties.AwsS3BucketProperties;
//import com.epam.entity.S3Location;
//import com.epam.mapper.S3LocationMapper;
//import io.awspring.cloud.s3.Location;
//import io.awspring.cloud.s3.S3Resource;
//import io.awspring.cloud.s3.S3Template;
//import java.io.IOException;
//import java.net.URL;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import unit.objectmothers.ObjectMother;
//
//@ExtendWith(MockitoExtension.class)
//public class S3LocationMapperTest {
//
//  @Mock
//  private S3Resource s3Resource;
//
//  @Mock
//  private S3Location location;
//
//  @InjectMocks
//  private S3LocationMapper s3LocationMapper;
//
//  @Test
//  public void testToS3Location() throws IOException {
//    // Arrange
//    String bucketName = "my-bucket";
//    String objectName = "my-object";
//    String urlPath = "/my-bucket/my-object";
//
//    when(location.getBucket()).thenReturn(bucketName);
//    when(location.getObject()).thenReturn(objectName);
//    when(s3Resource.getLocation()).thenReturn(any(Location.class));
//    when(s3Resource.getURL()).thenReturn(new URL("https://s3.amazonaws.com" + urlPath));
//
//    // Act
//    S3Location s3Location = s3LocationMapper.toS3Location(s3Resource);
//
//    // Assert
//    assertThat(s3Location).isNotNull();
//    assertThat(s3Location.getBucket()).isEqualTo(bucketName);
//    assertThat(s3Location.getObject()).isEqualTo(objectName);
//    assertThat(s3Location.getPath()).isEqualTo(urlPath);
//  }
//
//}
