package org.example.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    StatusRepository statusRepository;

//    @PostConstruct
//    public void init() {
//        // Initialize default statuses
//        if (statusRepository.count() == 0) { // Check if the statuses are already initialized
//            Status status1 = new Status("NEW");
//            Status status2 = new Status("IN_PROGRESS");
//            Status status3 = new Status("COMPLETED");
//
//            // Save them to the repository
//            statusRepository.save(status1);
//            statusRepository.save(status2);
//            statusRepository.save(status3);
//        }
//    }
}
