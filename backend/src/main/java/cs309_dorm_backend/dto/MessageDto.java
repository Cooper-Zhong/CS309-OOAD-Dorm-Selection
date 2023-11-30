package cs309_dorm_backend.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    @ApiModelProperty
    @NotBlank
    private int messageId;

    @ApiModelProperty
    @NotBlank
    private boolean isRead;

    public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
    }
    public int getMessageOwner() {
            return messageOwnerId;
    }
    public boolean isRead() {
            return isRead;
    }

    public void setRead(boolean read) {
            isRead = read;
    }

    @Getter
    @ApiModelProperty
    @NotBlank
    private String messageContent;
    @ApiModelProperty
    @NotBlank
    private String messageTitle;
    @Getter
    @ApiModelProperty
    @NotBlank
    private String messageTime;
    @ApiModelProperty
    @NotBlank
    private int messageOwnerId;

    @ApiModelProperty
    @NotBlank
    private int messageSenderId;

    public int getMessageSenderId() {
        return messageSenderId;
    }
}
