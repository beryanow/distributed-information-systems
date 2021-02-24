package ru.nsu.g.beryanov.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import ru.nsu.g.beryanov.model.Node;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
public class NodeDTO {
    private long id;
    private int version;
    private Date timestamp;
    private long uid;
    private String username;
    private long changeset;
    private Double longitude;
    private Double latitude;
    private List<TagDTO> tags;

    public static NodeDTO addTags(Node node) {
        List<TagDTO> tags = node.getTag().stream()
                .map(tag -> TagDTO.wrapNodeId(tag, node.getId()))
                .collect(Collectors.toList());

        return NodeDTO.builder()
                .id(node.getId())
                .version(node.getVersion())
                .timestamp(new Date(node.getTimestamp().toGregorianCalendar().getTime().getTime()))
                .uid(node.getUid())
                .username(node.getUser())
                .changeset(node.getChangeset())
                .latitude(node.getLat())
                .longitude(node.getLon())
                .tags(tags)
                .build();
    }
}
