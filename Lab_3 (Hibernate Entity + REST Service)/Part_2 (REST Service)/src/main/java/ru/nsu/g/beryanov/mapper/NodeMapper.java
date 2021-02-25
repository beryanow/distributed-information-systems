package ru.nsu.g.beryanov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.entity.NodeEntity;
import ru.nsu.g.beryanov.model.Node;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface NodeMapper {
    NodeDTO toDto(NodeEntity nodeEntity);
    NodeEntity toEntity (NodeDTO nodeDTO);

    @Mapping(target = "timestamp", expression = "java(new java.sql.Date(node.getTimestamp().toGregorianCalendar().getTime().getTime()))")
    @Mapping(target = "username", source = "user")
    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "longitude", source = "lon")
    @Mapping(target = "tags", source = "tag")
    NodeDTO toDtoFromXMLWrapper(Node node);

    List<NodeDTO> toDtos(List<NodeEntity> nodeEntities);
}
