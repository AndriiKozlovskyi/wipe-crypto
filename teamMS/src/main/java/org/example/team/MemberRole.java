package org.example.team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "members_roles")
public class MemberRole {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
