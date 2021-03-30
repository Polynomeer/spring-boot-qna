package com.codesquad.qna.domain;

import com.codesquad.qna.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @CreatedDate
    private LocalDateTime createdDateTime;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public String getCreatedTime() {
        return DateTimeUtils.formatByPattern(createdDateTime);
    }

    public void delete() {
        deleted = true;
    }
}
