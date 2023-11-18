package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    @ApiModelProperty
    @NotBlank
    private int studentId;

    @ApiModelProperty
    @NotBlank
    private int buildingId;

    @ApiModelProperty
    @NotBlank
    private int roomNumber;

}
