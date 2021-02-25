package ru.nsu.g.beryanov.utility;

import lombok.experimental.UtilityClass;
import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.dto.TagDTO;
import ru.nsu.g.beryanov.model.Node;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Converter {
    public static NodeDTO convertToNodeDTO(Node node) {
        List<TagDTO> tags = node.getTag().stream()
                .map(tag -> convertToTagDTO(tag, node.getId()))
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

    public static TagDTO convertToTagDTO(Node.Tag tag, long nodeId) {
        return TagDTO.builder()
                .nodeId(nodeId)
                .key(tag.getK())
                .value(tag.getV())
                .build();
    }
}
