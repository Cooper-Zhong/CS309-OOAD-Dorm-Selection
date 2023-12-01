package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterLeaderDto {

    @ApiModelProperty
    @NotBlank
    private String oldId;

    @ApiModelProperty
    @NotBlank
    private String leaderId; // new admin of this team
}
