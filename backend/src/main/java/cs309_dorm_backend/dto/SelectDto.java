package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectDto {

    @ApiModelProperty
    @NotBlank
    private int roomId;

    @ApiModelProperty
    @NotBlank
    private int teamId;
}
