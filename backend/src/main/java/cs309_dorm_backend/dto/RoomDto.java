package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    @ApiModelProperty
    @NotBlank
    private int buildingId;

    @ApiModelProperty
    @NotBlank
    private int roomNumber;

    @ApiModelProperty
    @NotBlank
    private int floor;

    @ApiModelProperty
    @NotBlank
    private int roomType;

    @ApiModelProperty
    @NotBlank
    private String gender;

    @ApiModelProperty
    private String description;
}
