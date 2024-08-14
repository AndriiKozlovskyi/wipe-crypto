package org.example.account;

import jakarta.persistence.EntityNotFoundException;
import org.example.account.dtos.AccountRequest;
import org.example.account.dtos.AccountResponse;
import org.example.client.UserResponse;
import org.example.client.UserServiceClient;
import org.example.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserServiceClient userServiceClient;

    public Set<AccountResponse> all() {
        Set<Account> accountSet = new HashSet<>(accountRepository.findAll());
        return AccountMapper.INSTANCE.toDtos(accountSet);
    }

    public Set<AccountResponse> allForEvent(Integer eventId) {

        Set<Account> accountSet = new HashSet<>(accountRepository.findByEventId(eventId));
        return AccountMapper.INSTANCE.toDtos(accountSet);
    }


    public Set<Account> findAccountsByIds(Set<Integer> ids) {
        return new HashSet<>(accountRepository.findAllById(new ArrayList<>(ids)));
    }

    public Account findAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Account with id: " + id + " not found"));
    }

    public AccountResponse getById(Integer id) {
        return AccountMapper.INSTANCE.toDto(findAccountById(id));
    }

    public AccountResponse create(Integer eventId, AccountRequest accountRequest, HttpHeaders headers) {
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        Account account = new Account();
        account.setName(accountRequest.getName());
        account.setEventId(eventId);
        assert user != null;
        account.setCreatedBy(user.getId());
        account.setCreatedAt(OffsetDateTime.now());
        accountRepository.save(account);
        return AccountMapper.INSTANCE.toDto(account);
    }

    public AccountResponse update(Integer id, AccountRequest request, HttpHeaders headers) {
        Account account = findAccountById(id);
        UserResponse user = userServiceClient.getUserFromHeaders(headers).getBody();

        account.setName(request.getName());
        assert user != null;
        account.setUpdatedBy(user.getId());
        account.setUpdatedAt(OffsetDateTime.now());
        accountRepository.save(account);
        return AccountMapper.INSTANCE.toDto(account);
    }

    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

}
