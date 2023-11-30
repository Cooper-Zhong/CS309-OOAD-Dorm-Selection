package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUpdateDto {

    @ApiModelProperty
    @NotBlank
    private int messageId;

    @ApiModelProperty
    private boolean isRead;

    @ApiModelProperty
    private String messageContent;

    @ApiModelProperty
    private String messageTitle;

}
