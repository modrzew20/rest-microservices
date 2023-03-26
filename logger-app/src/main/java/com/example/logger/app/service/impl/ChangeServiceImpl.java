package com.example.logger.app.service.impl;

import com.example.logger.app.document.ChangeDocument;
import com.example.logger.app.mapper.ChangeMapper;
import com.example.logger.app.model.Change;
import com.example.logger.app.repository.ChangeRepository;
import com.example.logger.app.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeServiceImpl implements ChangeService {

    private final ChangeRepository changeRepository;
    private final ChangeMapper changeMapper;

    @Override
    public List<Change> getAll(UUID id) {
        return changeRepository.findByShapeId(id)
                .stream().map(changeMapper::changeDocumentToChange).toList();
    }

    @Override
    public Change create(Change newChange) {
        ChangeDocument document = changeMapper.changeToChangeDocument(newChange);
        return changeMapper.changeDocumentToChange(changeRepository.save(document));
    }
}
