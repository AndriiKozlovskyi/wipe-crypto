package org.example.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventParticipationRepository extends JpaRepository<EventParticipation, Integer> {
    List<EventParticipation> findByEventId(Integer eventId);
    @Query("SELECT e FROM Event e JOIN e.participations p WHERE p.participantId = :participantId")
    Set<Event> findEventsByParticipantId(@Param("participantId") Integer participantId);
    Optional<EventParticipation> findByEventIdAndParticipantId(Integer eventId, Integer participantId);
}