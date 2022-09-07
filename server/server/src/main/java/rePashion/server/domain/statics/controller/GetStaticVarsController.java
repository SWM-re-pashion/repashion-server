package rePashion.server.domain.statics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.statics.exception.StaticVariableNotExisted;
import rePashion.server.domain.statics.service.StaticService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.error.exception.ErrorCode;

@RestController
@RequestMapping("/api/static")
@RequiredArgsConstructor
public class GetStaticVarsController {

    private final StaticService staticService;

    @GetMapping("{type}")
    public ResponseEntity<GlobalResponse> get(@PathVariable String type){
        Object dto = new Object();
        try{
            dto = staticService.setMethodBy(StaticService.Type.valueOf(type));
        }catch(IllegalArgumentException e){
            throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        }
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, dto), HttpStatus.OK);
    }
}
