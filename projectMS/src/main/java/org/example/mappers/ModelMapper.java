package org.example.mappers;

import java.util.List;

import org.example.entity.TableEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ModelMapper {
    default Integer toId(TableEntity entity) {
        if(entity == null) {
            return null;
        }
        return entity.getId();
    }

    List<Integer> toIds(List<TableEntity> entities);
}
