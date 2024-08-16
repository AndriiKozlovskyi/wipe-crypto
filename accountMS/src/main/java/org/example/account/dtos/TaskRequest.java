package org.example.account.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskRequest {
    private String name;
    private boolean completed;
    private Integer accountId;
    private Integer eventId;
}
