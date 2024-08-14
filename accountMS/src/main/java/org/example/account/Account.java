package org.example.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.TableEntity;
import org.example.finance.Finance;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "account")
public class Account implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer eventId;
    private String notes;
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Finance> finances = new HashSet<>();
    private Integer createdBy;
    private Integer updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime updatedAt;
}
