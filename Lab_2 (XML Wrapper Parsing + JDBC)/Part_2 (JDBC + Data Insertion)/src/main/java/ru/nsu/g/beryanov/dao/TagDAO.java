package ru.nsu.g.beryanov.dao;

import ru.nsu.g.beryanov.dto.TagDTO;

import java.util.List;

public interface TagDAO {
    List<TagDTO> getTags(long nodeId);
    void insertTag(TagDTO tag);
    void insertPreparedTag(TagDTO tag);
    void batchInsertTags(List<TagDTO> tagDTOs);
}
