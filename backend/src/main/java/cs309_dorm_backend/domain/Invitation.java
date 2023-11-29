package cs309_dorm_backend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invitations", uniqueConstraints = {@UniqueConstraint(name = "unique_invitation",columnNames = {"team_id", "student_id"})})
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private int invitationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invitation_team", value = ConstraintMode.CONSTRAINT))
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invitation_student", value = ConstraintMode.CONSTRAINT))
    private Student student;

    @Column(name = "is_invitation", nullable = false)
    private boolean isInvitation;  // true: invitation, false: application


}
