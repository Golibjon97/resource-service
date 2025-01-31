package com.epam.exception.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;


@Builder
public record ExceptionInformation(HttpStatus status, String code, String message) {
}
