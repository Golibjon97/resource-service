package com.epam.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "s3location")
public class S3Location {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  private String bucket;
  private String object;
  private String path;
  private String status = "STAGING";
}
