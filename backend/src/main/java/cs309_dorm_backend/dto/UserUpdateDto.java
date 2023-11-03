package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @ApiModelProperty
    @NotBlank
    private int campusId;

    @ApiModelProperty
    @NotBlank
    private String oldPassword;

    @ApiModelProperty
    @Length(min = 6, message = "Password need at least 6 bits")
    @NotBlank
    private String newPassword;
}
