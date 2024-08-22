package org.example.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.account.Account;
import org.example.entity.TableEntity;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "finances")
public class Finance implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private double amount;
    private String tokenName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="finance_id")
    @EqualsAndHashCode.Exclude
    private Account account;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
