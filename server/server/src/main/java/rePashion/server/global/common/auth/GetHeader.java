package rePashion.server.global.common.auth;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetHeader {
    public String of(Map<String, String> headers, String key){
        return headers.get(key);
    }
}
