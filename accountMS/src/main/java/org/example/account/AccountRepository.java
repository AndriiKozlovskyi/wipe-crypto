package org.example.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Set<Account> findByEventId(Integer eventId);
}
