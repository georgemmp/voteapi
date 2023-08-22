package com.softbox.voteapi.infrastructure.adapter.associate.mapper;

import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.api.dto.AssociateDTO;
import org.springframework.beans.BeanUtils;

public class AssociateMapper {

    private AssociateMapper() {

    }

    public static Associate mapper(AssociateDTO dto) {
        Associate associate = new Associate();

        BeanUtils.copyProperties(dto, associate);

        return associate;
    }
}
