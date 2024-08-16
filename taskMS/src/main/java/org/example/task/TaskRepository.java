package org.example.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Set<Task> findByEventIdAndAccountId(Integer eventId, Integer accountId);
    Set<Task> findByAccountId(Integer accountId);
}
