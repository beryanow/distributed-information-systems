package ru.nsu.g.beryanov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nsu.g.beryanov.dto.TagDTO;
import ru.nsu.g.beryanov.entity.TagEntity;
import ru.nsu.g.beryanov.model.Node;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDto(TagEntity tagEntity);

    @Mapping(target = "id", ignore = true)
    TagEntity toEntity (TagDTO tagDTO);

    @Mapping(target = "key", source = "k")
    @Mapping(target = "value", source = "v")
    TagDTO toDtoFromXMLWrapper(Node.Tag tag);
}
