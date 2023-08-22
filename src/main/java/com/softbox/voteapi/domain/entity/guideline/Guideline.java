package com.softbox.voteapi.domain.entity.guideline;

import com.softbox.voteapi.domain.utils.DateHandlerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guideline {
    @Id
    private String guidelineId;
    private String description;
    private Boolean session;
    private String result;
    private LocalDateTime date;

    public Boolean verifyIfSessionIsOpen() {
        return Boolean.TRUE.equals(this.session);
    }

    public Guideline openSession() {
        this.session = true;
        this.date = LocalDateTime.now();

        return this;
    }

    public Guideline closeSession() {
        long diff = DateHandlerUtil.getDiffMinutes(this.date, LocalDateTime.now());

        if (diff >= 1L) {
            this.session = false;
        }

        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
