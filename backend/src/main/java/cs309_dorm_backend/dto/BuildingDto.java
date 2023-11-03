package cs309_dorm_backend.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto {

    @ApiModelProperty
    @NotBlank
    private int buildingId;

    @ApiModelProperty
    @NotBlank
    private int maxHeight;

    @ApiModelProperty
    @NotBlank
    private String zoneName;
}
