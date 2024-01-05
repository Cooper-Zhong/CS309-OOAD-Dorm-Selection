package cs309_dorm_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDto {

    @NotBlank
    private String email;

    @NotBlank
    private String verificationCode;
}
