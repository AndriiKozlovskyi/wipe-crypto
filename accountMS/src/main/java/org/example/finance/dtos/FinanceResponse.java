package org.example.finance.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.account.Account;

import java.time.OffsetDateTime;

@Data
public class FinanceResponse {
    private Integer id;
    private double amount;
    private String tokenName;
    private Integer account;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
