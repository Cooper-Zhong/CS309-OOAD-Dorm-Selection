package cs309_dorm_backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondCommentDto {
    @ApiModelProperty
    @NotBlank
    private int authorId;

    @ApiModelProperty
    @NotBlank
    private int parentId;

    @ApiModelProperty
    @NotBlank
    private String content;
}
