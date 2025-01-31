package com.epam.controller;

import com.epam.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static com.epam.util.ValidateParam.*;

@RestController
@RequestMapping("/api/v1/resources")
@Slf4j
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(value = "/upload_mp3", consumes = "audio/mpeg")
    public ResponseEntity<?> uploadResource(@RequestBody byte[] mp3Data){
        Integer mp3FileId = resourceService.saveFile(mp3Data);
        return ResponseEntity.status(HttpStatus.OK).body(mp3FileId);
    }

    @GetMapping("/mp3/{id}")
    public ResponseEntity<?> getByteMp3(@PathVariable(value = "id") Integer resourceId,
                                        @RequestHeader(value = "User-Agent", required = false) String userAgent) throws IOException {
        byte[] bytes = resourceService.getMp3File(resourceId, userAgent);
        if (Objects.isNull(bytes)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(bytes);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestParam(value = "ids") String ids) {
        if (isNotValid(ids)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("IDs length should be less than 200 characters");
        }
        resourceService.deleteFiles(ids);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
