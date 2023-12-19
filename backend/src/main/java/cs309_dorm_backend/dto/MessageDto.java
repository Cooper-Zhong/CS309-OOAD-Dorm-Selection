package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {


    @ApiModelProperty
    @NotBlank
    private String senderId;

    @ApiModelProperty
    @NotBlank
    private String senderName;

    @ApiModelProperty
    @NotBlank
    private String receiverId;

    @ApiModelProperty
    @NotBlank
    private String content;

}
