package org.example.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Configuration
public class DataInitializer {

    @Autowired
    StatusRepository statusRepository;

    @Bean
    @Transactional
    public CommandLineRunner initStatuses(StatusRepository statusRepository) {
        return args -> {
            if (statusRepository.count() == 0) {
                Status statusTodo = Status.builder()
                        .name("todo")
                        .events(new HashSet<>())
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();
                Status statusDeposited = Status.builder()
                        .name("deposited")
                        .events(new HashSet<>())
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();
                Status statusRewarded = Status.builder()
                        .name("rewarded")
                        .events(new HashSet<>())
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();
                Status statusRevenue = Status.builder()
                        .name("revenue")
                        .events(new HashSet<>())
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();
                Status statusFailed = Status.builder()
                        .name("failed")
                        .events(new HashSet<>())
                        .createdAt(null)
                        .createdBy(null)
                        .updatedBy(null)
                        .updatedAt(null)
                        .build();

                statusRepository.save(statusTodo);
                statusRepository.save(statusDeposited);
                statusRepository.save(statusRewarded);
                statusRepository.save(statusRevenue);
                statusRepository.save(statusFailed);

            }
        };
    }
}
