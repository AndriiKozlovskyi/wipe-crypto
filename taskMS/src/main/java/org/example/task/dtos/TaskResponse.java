package org.example.task.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TaskResponse {
    private Integer id;
    private String name;
    private boolean completed;
    private Integer accountId;
    private Integer eventId;
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
