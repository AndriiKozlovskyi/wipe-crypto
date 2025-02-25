package org.example.event_type;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.TableEntity;
import org.example.event.Event;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "event_types")
public class EventType implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Event> events = new HashSet<>();
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
