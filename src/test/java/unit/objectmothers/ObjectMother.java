package unit.objectmothers;

import com.epam.entity.Mp3File;
import com.epam.entity.S3Location;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3OutputStream;
import io.awspring.cloud.s3.S3OutputStreamProvider;
import io.awspring.cloud.s3.S3Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.util.ResourceUtils;
import software.amazon.awssdk.services.s3.S3Client;

public class ObjectMother {

  public static byte[] mp3Data() throws IOException {

    File songFile = ResourceUtils.getFile("classpath:test-song.mp3");
    return Files.readAllBytes(songFile.toPath());

  }

  public static Mp3File mp3File() throws IOException {
    Mp3File mp3File = new Mp3File();
    mp3File.setData(mp3Data());
    mp3File.setName("Tu principe");
    return mp3File;
  }

  public static S3Location s3Location(){
    S3Location s3Location = new S3Location();
    s3Location.setId(1);
    s3Location.setBucket("bucket");
    s3Location.setObject("object");
    s3Location.setPath("Path");
    return s3Location;
  }

  public static S3Resource s3Resource(){
    S3Client s3Client = new S3Client() {
      @Override
      public String serviceName() {
        return null;
      }

      @Override
      public void close() {

      }
    };

    S3OutputStreamProvider s3OutputStreamProvider = new S3OutputStreamProvider() {
      @Override
      public S3OutputStream create(String bucket, String key, ObjectMetadata metadata)
          throws IOException {
        return null;
      }
    };

    return new S3Resource("Location", s3Client, s3OutputStreamProvider);
  }

}
