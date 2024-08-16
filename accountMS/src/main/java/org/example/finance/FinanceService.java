package org.example.finance;

import jakarta.persistence.EntityNotFoundException;
import org.example.account.Account;
import org.example.account.AccountRepository;
import org.example.client.UserResponse;
import org.example.client.UserServiceClient;
import org.example.finance.dtos.FinanceRequest;
import org.example.finance.dtos.FinanceResponse;
import org.example.mapper.FinanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class FinanceService {
    @Autowired
    FinanceRepository financeRepository;
    @Autowired
    UserServiceClient userServiceClient;
    @Autowired
    AccountRepository accountRepository;

    public Set<FinanceResponse> all() {
        Set<Finance> financeSet = new HashSet<>(financeRepository.findAll());
        return FinanceMapper.INSTANCE.toDtos(financeSet);
    }

    public Set<Finance> findFinancesByIds(Set<Integer> ids) {
        return new HashSet<>(financeRepository.findAllById(new ArrayList<>(ids)));
    }

    public Finance findFinanceById(Integer id) {
        return financeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Finance with id: " + id + " not found"));
    }

    public FinanceResponse getById(Integer id) {
        return FinanceMapper.INSTANCE.toDto(findFinanceById(id));
    }

    public FinanceResponse create(Integer accountId, FinanceRequest financeRequest, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(("Account with " + accountId + " not found")));
        Finance finance = new Finance();
        finance.setAccount(account);
        finance.setAmount(financeRequest.getAmount());
        finance.setTokenName(financeRequest.getTokenName());
        finance.setDate(OffsetDateTime.now());
        assert user != null;
        finance.setCreatedBy(user.getId());
        finance.setCreatedAt(OffsetDateTime.now());
        financeRepository.save(finance);
        return FinanceMapper.INSTANCE.toDto(finance);
    }

    public FinanceResponse update(Integer id, FinanceRequest request, HttpHeaders headers) {
        Finance finance = findFinanceById(id);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();
        finance.setAmount(request.getAmount());
        finance.setTokenName(request.getTokenName());
        finance.setDate(request.getDate());
        assert user != null;
        finance.setUpdatedBy(user.getId());
        finance.setUpdatedAt(OffsetDateTime.now());
        financeRepository.save(finance);
        return FinanceMapper.INSTANCE.toDto(finance);
    }

    public void delete(Integer id) {
        financeRepository.deleteById(id);
    }

}
