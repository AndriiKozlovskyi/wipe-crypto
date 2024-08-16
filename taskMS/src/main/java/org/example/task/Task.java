package org.example.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.example.entity.TableEntity;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "tasks")
public class Task implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private boolean completed;
    private Integer accountId;
    private Integer eventId;
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
