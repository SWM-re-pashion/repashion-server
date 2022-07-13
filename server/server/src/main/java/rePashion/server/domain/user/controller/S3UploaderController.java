package rePashion.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rePashion.server.domain.user.service.S3UploaderService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3UploaderController {

    private final S3UploaderService s3UploaderService;

    @PostMapping("/upload")
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "static");
    }
}
