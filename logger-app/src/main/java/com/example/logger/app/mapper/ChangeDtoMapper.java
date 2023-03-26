package com.example.logger.app.mapper;

import com.example.logger.app.dto.ChangeDto;
import com.example.logger.app.model.Change;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangeDtoMapper {

    Change changeDtoToChange(ChangeDto dto);

    ChangeDto changeToChangeDto(Change change);
}
