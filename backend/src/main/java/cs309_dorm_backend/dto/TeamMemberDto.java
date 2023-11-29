package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDto {

    @ApiModelProperty
    @NotBlank
    private String creatorId;

    @ApiModelProperty
    @NotBlank
    private String studentId; // member or new admin of this team
}
