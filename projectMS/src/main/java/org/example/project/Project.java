package org.example.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.TableEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "projects")
public class Project implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String link;
    private String description;
    @ElementCollection
    private Set<Integer> followerIds = new HashSet<>();
    @ElementCollection
    private Set<Integer> memberIds = new HashSet<>();
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
    private boolean isPublic;
}
