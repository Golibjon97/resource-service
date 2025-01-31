package com.epam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VTestController {

    @GetMapping("/vTest")
    public ResponseEntity<?> vTest() {
        return ResponseEntity.ok().build();
    }
}