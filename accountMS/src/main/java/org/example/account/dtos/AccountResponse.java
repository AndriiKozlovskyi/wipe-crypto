package org.example.account.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.finance.Finance;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class AccountResponse {
    private Integer id;
    private String name;
    private Integer eventId;
    private String notes;
    private Set<Integer> finances = new HashSet<>();
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
