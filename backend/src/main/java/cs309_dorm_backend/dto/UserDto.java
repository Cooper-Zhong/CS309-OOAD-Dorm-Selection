package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @ApiModelProperty
    private String campusId;
    @ApiModelProperty
    private String role;
    @ApiModelProperty
    private String password;

}
