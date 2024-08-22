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
            if (financeRepository.count() == 0) {
                Finance financeDeposit = Finance.builder()
                        .name("Deposit")
                        .tokenName("$")
                        .amount(0)
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();
                Finance financeWithdraw = Finance.builder()
                        .name("Withdraw")
                        .tokenName("$")
                        .amount(0)
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();
                Finance financeIncome = Finance.builder()
                        .name("Income")
                        .tokenName("$")
                        .amount(0)
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();

                financeRepository.save(financeDeposit);
                financeRepository.save(financeWithdraw);
                financeRepository.save(financeIncome);
            }
        };
    }
}
