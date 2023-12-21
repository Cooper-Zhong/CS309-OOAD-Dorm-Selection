package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private String receiverName;

    @ApiModelProperty
    @NotBlank
    private String content;

    private boolean read;

    private int messageId;

}
