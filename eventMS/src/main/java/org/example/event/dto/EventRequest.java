package org.example.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EventRequest {
    private String name;
    private String link;
    private String description;
    private Integer projectId;
    private Integer statusId;
    private Integer eventTypeId;
    private boolean isPublic;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;
}
