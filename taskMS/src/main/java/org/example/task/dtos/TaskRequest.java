package org.example.task.dtos;

import lombok.Data;

@Data
public class TaskRequest {
    private String name;
    private boolean completed;
    private Integer accountId;
    private Integer eventId;
}
