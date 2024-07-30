package com.authority.entity;

import com.authority.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<User> users;
}
