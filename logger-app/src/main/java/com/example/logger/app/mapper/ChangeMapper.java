package com.example.logger.app.mapper;

import com.example.logger.app.document.ChangeDocument;
import com.example.logger.app.model.Change;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangeMapper {

    Change changeDocumentToChange(ChangeDocument changeDocument);

    ChangeDocument changeToChangeDocument(Change change);

}
