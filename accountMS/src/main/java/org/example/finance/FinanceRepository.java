package org.example.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FinanceRepository extends JpaRepository<Finance, Integer> {

}

