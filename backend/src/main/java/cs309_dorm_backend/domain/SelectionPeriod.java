package cs309_dorm_backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "select_period")
public class SelectionPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer periodId;

    private String degree;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //beijing time
    private Timestamp endTime;

}
