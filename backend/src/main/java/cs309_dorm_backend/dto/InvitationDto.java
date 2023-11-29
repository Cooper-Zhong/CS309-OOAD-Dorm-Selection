package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto {

    @ApiModelProperty
    @NotBlank
    private int teamId;

    @ApiModelProperty
    @NotBlank
    private String studentId;

    @ApiModelProperty
    @NotBlank
    private boolean invitation;  // true: invitation, false: application
}
