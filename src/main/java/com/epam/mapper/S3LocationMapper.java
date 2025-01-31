package com.epam.mapper;

import com.epam.entity.S3Location;
import io.awspring.cloud.s3.S3Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;


@Mapper(config = MappersConfig.class)
public interface S3LocationMapper {

  @Mapping(source = "location.bucket", target = "bucket")
  @Mapping(source = "location.object", target = "object")
  @Mapping(expression = "java( getUrlSafe(s3Resource) )", target = "path")
  S3Location toS3Location(S3Resource s3Resource);

  default String getUrlSafe(S3Resource s3Resource) {
    try {
      return s3Resource.getURL().toString();
    } catch (IOException e) {
      return "default/path";
    }
  }

}
