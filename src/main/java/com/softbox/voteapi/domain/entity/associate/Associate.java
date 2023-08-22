package com.softbox.voteapi.domain.entity.associate;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {
    @Id
    private String associateId;
    private String cpf;
    private String name;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
