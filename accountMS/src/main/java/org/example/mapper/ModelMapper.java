package org.example.mapper;

import org.example.entity.TableEntity;
import org.mapstruct.Mapper;

import java.util.List;

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
