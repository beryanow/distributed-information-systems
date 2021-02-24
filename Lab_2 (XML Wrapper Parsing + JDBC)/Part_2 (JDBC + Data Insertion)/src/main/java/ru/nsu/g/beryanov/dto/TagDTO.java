package ru.nsu.g.beryanov.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import ru.nsu.g.beryanov.model.Node;

@Builder
@Setter
@Getter
public class TagDTO {
    private long nodeId;
    private String key;
    private String value;

    public static TagDTO wrapNodeId(Node.Tag tag, long nodeId) {
        return TagDTO.builder()
                .nodeId(nodeId)
                .key(tag.getK())
                .value(tag.getV())
                .build();
    }
}
