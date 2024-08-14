package org.example.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.TableEntity;
import org.example.event_type.EventType;
import org.example.status.Status;

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
@Table(name = "events")
public class Event implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private String link;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    @EqualsAndHashCode.Exclude
    private EventType eventType;
    private ArrayList<Integer> participantIds = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_status_id")
    @EqualsAndHashCode.Exclude
    private Status status;
    private Integer projectId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
    private boolean isPublic;
}
