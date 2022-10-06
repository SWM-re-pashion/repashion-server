package rePashion.server.domain.statics.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CommonResponse{
        @Getter
        @AllArgsConstructor
        public static class Top{
            private String name;
            private String code;
        }
        @Getter
        @AllArgsConstructor
        public static class Bottom{
            private String name;
            private String code;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String name;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String code;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<Top> top = new ArrayList<>();

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        List<Bottom> bottom = new ArrayList<>();
    }
}
