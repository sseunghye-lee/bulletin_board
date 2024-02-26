package com.seung.pilot.commons;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    protected  Long createdBy;

    protected LocalDateTime createdDate;

    protected Long modifiedBy;

    protected LocalDateTime modifiedDate;
}
