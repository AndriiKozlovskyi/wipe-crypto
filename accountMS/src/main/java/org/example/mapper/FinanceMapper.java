package org.example.mapper;


import org.example.finance.Finance;
import org.example.finance.dtos.FinanceRequest;
import org.example.finance.dtos.FinanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, AccountMapper.class })
public interface FinanceMapper extends BaseDtoMapper<Finance, FinanceRequest, FinanceResponse> {
    FinanceMapper INSTANCE = Mappers.getMapper(FinanceMapper.class);
}
