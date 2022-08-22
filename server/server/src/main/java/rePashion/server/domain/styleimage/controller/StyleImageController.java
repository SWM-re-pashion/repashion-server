package rePashion.server.domain.styleimage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.styleimage.dto.StyleImageResponseDto;
import rePashion.server.domain.styleimage.service.StyleImageService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;

@RestController
@RequestMapping("/api/style-image")
@RequiredArgsConstructor
public class StyleImageController {

    private final StyleImageService styleImageService;

    @GetMapping("/save")
    public ResponseEntity<?> create(){
        styleImageService.create();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse> get(){
        StyleImageResponseDto dto = styleImageService.get();
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }
}
