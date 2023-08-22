package com.softbox.voteapi.infrastructure.adapter.associate;

import com.softbox.voteapi.application.useCase.associate.SaveAssociate;
import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.adapter.associate.mapper.AssociateMapper;
import com.softbox.voteapi.infrastructure.api.dto.AssociateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveAssociateAdapter {

    private final SaveAssociate saveAssociate;

    public Mono<Associate> execute(AssociateDTO dto) {
        Associate associate = AssociateMapper.mapper(dto);
        return this.saveAssociate.execute(associate);
    }
}
