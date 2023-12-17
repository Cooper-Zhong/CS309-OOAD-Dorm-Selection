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
public class CommentDto {
    @ApiModelProperty
    @NotBlank
    private String authorId;

    @ApiModelProperty
    @NotBlank
    private String authorName;

    @ApiModelProperty
    @NotBlank
    private int buildingId;

    @ApiModelProperty
    @NotBlank
    private int roomNumber;

    @ApiModelProperty
    @NotBlank
    private String content;

}
