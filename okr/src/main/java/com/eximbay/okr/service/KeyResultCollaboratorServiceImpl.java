package com.eximbay.okr.service;

import com.eximbay.okr.dto.keyResultCollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.entity.KeyResultCollaborator;
import com.eximbay.okr.repository.KeyResultCollaboratorRepository;
import com.eximbay.okr.service.Interface.IKeyResultCollaboratorService;
import com.eximbay.okr.service.specification.KeyResultCollaboratorQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class KeyResultCollaboratorServiceImpl implements IKeyResultCollaboratorService {
    private final KeyResultCollaboratorRepository keyResultCollaboratorRepository;
    private final KeyResultCollaboratorQuery keyResultCollaboratorQuery;
    private final MapperFacade mapper;

    @Override
    public List<KeyResultCollaboratorDto> findAll() {
        List<KeyResultCollaborator> keyResultCollaborators = keyResultCollaboratorRepository.findAll();
        return mapper.mapAsList(keyResultCollaborators, KeyResultCollaboratorDto.class);
    }

    @Override
    public Optional<KeyResultCollaboratorDto> findById(Integer id) {
        Optional<KeyResultCollaborator> keyResultCollaborator = keyResultCollaboratorRepository.findById(id);
        return keyResultCollaborator.map(m-> mapper.map(m, KeyResultCollaboratorDto.class));
    }

    @Override
    public void remove(KeyResultCollaboratorDto keyResultCollaboratorDto) {
        KeyResultCollaborator keyResultCollaborator = mapper.map(keyResultCollaboratorDto, KeyResultCollaborator.class);
        keyResultCollaboratorRepository.delete(keyResultCollaborator);
    }

    @Override
    public KeyResultCollaboratorDto save(KeyResultCollaboratorDto keyResultCollaboratorDto) {
        KeyResultCollaborator keyResultCollaborator = mapper.map(keyResultCollaboratorDto, KeyResultCollaborator.class);
        keyResultCollaborator = keyResultCollaboratorRepository.save(keyResultCollaborator);
        return mapper.map(keyResultCollaborator, KeyResultCollaboratorDto.class);
    }

    @Override
    public List<KeyResultCollaboratorDto> findByKeyResultSeqIn(List<Integer> in) {
        List<KeyResultCollaborator> keyResultCollaborators = keyResultCollaboratorRepository.findAll(
                keyResultCollaboratorQuery.findActiveKeyResultCollaborator()
                .and(keyResultCollaboratorQuery.findByKeyResultSeqIn(in))
                .and(keyResultCollaboratorQuery.findByCollaboratorNotNull())
        );
        return mapper.mapAsList(keyResultCollaborators, KeyResultCollaboratorDto.class);
    }
}
