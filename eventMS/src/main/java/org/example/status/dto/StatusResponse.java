package org.example.status.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class StatusResponse {
    private Integer id;
    private String name;
    private Set<Integer> events = new HashSet<>();
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
