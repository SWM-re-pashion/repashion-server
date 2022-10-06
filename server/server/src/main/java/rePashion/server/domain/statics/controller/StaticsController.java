package rePashion.server.domain.statics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rePashion.server.domain.statics.exception.StaticVariableNotExisted;
import rePashion.server.domain.statics.model.Statics;
import rePashion.server.domain.statics.resources.Response;
import rePashion.server.domain.statics.service.StaticsService;
import rePashion.server.global.common.response.GlobalResponse;
import rePashion.server.global.common.response.StatusCode;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/statics")
@RequiredArgsConstructor
public class StaticsController {

    private final StaticsService staticsService;

    @GetMapping("/{type}")
    public ResponseEntity<GlobalResponse> get(@PathVariable String type){
        Statics.Type staticsType = getStaticsType(type);
        Object response = getResponse(staticsType);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    @GetMapping("/entire/{type}")
    public ResponseEntity<GlobalResponse> getIncludeEntireDate(@PathVariable String type){
        Statics.Type staticsType = getStaticsType(type);
        Object response = getResponseIncludeEntireData(staticsType);
        return new ResponseEntity<>(GlobalResponse.of(StatusCode.SUCCESS, response), HttpStatus.OK);
    }

    private Statics.Type getStaticsType(String type){
        try{
            return Statics.Type.valueOf(type);
        }catch(IllegalArgumentException e){
            throw new StaticVariableNotExisted(ErrorCode.STATIC_VARIABLE_NOT_EXISTED);
        }
    }

    private Object getResponse(Statics.Type type){
        List<Statics> commons = staticsService.getCommons(type);
        if(commons.isEmpty()){
            ArrayList<Response.CommonResponse.Top> tops = getTops(type, false);
            ArrayList<Response.CommonResponse.Bottom> bottoms = getBottoms(type, false);
            return new Response.CommonResponse(null, null, tops, bottoms);
        }else{
            List<Response.CommonResponse> singleResponse = new ArrayList<>();
            commons.forEach(o->singleResponse.add(new Response.CommonResponse(o.getName(), o.getCode(),null,null)));
            return singleResponse;
        }
    }

    private Object getResponseIncludeEntireData(Statics.Type type){
        List<Statics> commons = staticsService.getCommons(type);
        if(commons.isEmpty()){
            ArrayList<Response.CommonResponse.Top> tops = getTops(type, true);
            ArrayList<Response.CommonResponse.Bottom> bottoms = getBottoms(type, true);
            return new Response.CommonResponse(null, null, tops, bottoms);
        }else{
            List<Response.CommonResponse> singleResponse = new ArrayList<>();
            singleResponse.add(new Response.CommonResponse("전체", "all", null, null));
            commons.forEach(o->singleResponse.add(new Response.CommonResponse(o.getName(), o.getCode(),null,null)));
            return singleResponse;
        }
    }

    private ArrayList<Response.CommonResponse.Bottom> getBottoms(Statics.Type type, boolean isIncludeEntire) {
        ArrayList<Response.CommonResponse.Bottom> bottoms = new ArrayList<>();
        if(isIncludeEntire) bottoms.add(new Response.CommonResponse.Bottom("전체", "all"));
        staticsService.getBottoms(type).forEach(o-> bottoms.add(new Response.CommonResponse.Bottom(o.getName(), o.getCode())));
        return bottoms;
    }

    private ArrayList<Response.CommonResponse.Top> getTops(Statics.Type type, boolean isIncludeEntire) {
        ArrayList<Response.CommonResponse.Top> tops = new ArrayList<>();
        if(isIncludeEntire) tops.add(new Response.CommonResponse.Top("전체", "all"));
        staticsService.getTops(type).forEach(o-> tops.add(new Response.CommonResponse.Top(o.getName(), o.getCode())));
        return tops;
    }
}
