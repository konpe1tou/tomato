package com.example.tomato.external.entity;

import com.example.tomato.core.constants.UniqueIdType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "unique_id")
@Data
public class UniqueId {

    @Id
    private UniqueIdType uniqueIdType;

    @Version
    private Long latestCount;
}
