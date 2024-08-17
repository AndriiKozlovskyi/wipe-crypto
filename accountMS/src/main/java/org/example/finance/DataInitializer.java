package org.example.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Configuration
public class DataInitializer {

    @Autowired
    FinanceRepository financeRepository;

    @Bean
    @Transactional
    public CommandLineRunner initUsers(FinanceRepository financeRepository) {
        return args -> {
//            if (financeRepository.count() == 0) {
//                Finance financeDeposit = Finance.builder()
//                        .name("todo")
//                        .events(new HashSet<>())
//                        .createdAt(null)
//                        .createdBy(null)
//                        .updatedBy(null)
//                        .updatedAt(null)
//                        .build();
//                Finance financeDeposited = Finance.builder()
//                        .name("deposited")
//                        .events(new HashSet<>())
//                        .createdAt(null)
//                        .createdBy(null)
//                        .updatedBy(null)
//                        .updatedAt(null)
//                        .build();
//                Finance financeRewarded = Finance.builder()
//                        .name("rewarded")
//                        .events(new HashSet<>())
//                        .createdAt(null)
//                        .createdBy(null)
//                        .updatedBy(null)
//                        .updatedAt(null)
//                        .build();
//                Finance financeRevenue = Finance.builder()
//                        .name("revenue")
//                        .events(new HashSet<>())
//                        .createdAt(null)
//                        .createdBy(null)
//                        .updatedBy(null)
//                        .updatedAt(null)
//                        .build();
//                Finance financeFailed = Finance.builder()
//                        .name("failed")
//                        .events(new HashSet<>())
//                        .createdAt(null)
//                        .createdBy(null)
//                        .updatedBy(null)
//                        .updatedAt(null)
//                        .build();
//
//                financeRepository.save(financeTodo);
//                financeRepository.save(financeDeposited);
//                financeRepository.save(financeRewarded);
//                financeRepository.save(financeRevenue);
//                financeRepository.save(financeFailed);
//
//            }
        };
    }
}
