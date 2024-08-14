package org.example.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.event_type.dto.EventTypeResponse;
import org.example.status.dto.StatusResponse;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public class EventResponse {
    private Integer id;
    private String name;
    private String link;
    private String description;
    private Integer projectId;
    private StatusResponse status;
    private boolean isPublic;
    private EventTypeResponse eventType;
    private ArrayList<Integer> participantIds = new ArrayList<>();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
