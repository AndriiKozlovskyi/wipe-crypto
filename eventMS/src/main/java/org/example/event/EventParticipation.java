package org.example.event;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.TableEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_participations")
public class EventParticipation implements TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Event event;

    private Integer participantId;
}
