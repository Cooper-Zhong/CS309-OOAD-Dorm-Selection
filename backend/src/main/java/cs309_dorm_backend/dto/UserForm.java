package cs309_dorm_backend.dto;

import cs309_dorm_backend.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "user form")
public class UserForm {

    @NotBlank(message = "campusId shouldn't be null")
    @ApiModelProperty
    private int campusId;

    @NotBlank(message = "User role shouldn't be null")
    @ApiModelProperty
    private String role;

    @Length(min = 6, message = "Password need at least 6 bits")
    @ApiModelProperty
    private String password;

    @NotBlank(message = "Confirm password shouldn't be null")
    @ApiModelProperty
    private String confirmPassword;

    public boolean checkPasswordEquals() {
        return this.password.equals(this.confirmPassword);
    }

    public User convertToUser() {
        return new User(campusId, password, role);
    }
}
