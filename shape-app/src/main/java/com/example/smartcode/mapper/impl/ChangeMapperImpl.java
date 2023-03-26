package com.example.smartcode.mapper.impl;

import com.example.smartcode.dto.GetChangeDto;
import com.example.smartcode.entity.Change;
import com.example.smartcode.mapper.ChangeMapper;
import org.springframework.stereotype.Component;

@Component
public class ChangeMapperImpl implements ChangeMapper {

    @Override
    public GetChangeDto mapChangeToGetChangesDto(Change change) {
        GetChangeDto dto = new GetChangeDto();
        dto.setId(change.getId());
        dto.setChangedDate(change.getChangedDate());
        dto.setChangedBy(change.getChangedBy());
        dto.setParameterName(change.getParameterName());
        dto.setParameterOldValue(change.getParameterOldValue());
        dto.setParameterNewValue(change.getParameterNewValue());
        return dto;
    }
}
