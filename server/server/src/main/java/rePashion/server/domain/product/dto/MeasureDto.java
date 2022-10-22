package rePashion.server.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class MeasureDto {

    @JsonProperty
    private Integer length;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer shoulderWidth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer chestSection;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sleeveLength;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer waistSection;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer bottomSection;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer thighSection;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer rise;
}
