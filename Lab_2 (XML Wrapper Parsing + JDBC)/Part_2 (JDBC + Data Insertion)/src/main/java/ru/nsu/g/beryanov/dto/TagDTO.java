package ru.nsu.g.beryanov.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TagDTO {
    private long nodeId;
    private String key;
    private String value;
}
