package org.example.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Set<Event> findByProjectId(Integer projectId);
    Set<Event> findByCreatedBy(Integer userId);

}