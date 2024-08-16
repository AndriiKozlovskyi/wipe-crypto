package org.example.mapper;

import org.example.account.Account;
import org.example.account.dtos.AccountRequest;
import org.example.account.dtos.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, FinanceMapper.class })
public interface AccountMapper extends BaseDtoMapper<Account, AccountRequest, AccountResponse> {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
}
