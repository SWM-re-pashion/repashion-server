package rePashion.server.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @RequestMapping("/health-check")
    public ResponseEntity<?> checking(){
        return new ResponseEntity<>("health",HttpStatus.OK);
    }
}
