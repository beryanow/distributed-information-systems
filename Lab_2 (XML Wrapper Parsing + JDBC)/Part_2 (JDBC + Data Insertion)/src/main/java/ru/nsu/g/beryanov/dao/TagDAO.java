package ru.nsu.g.beryanov.dao;

import ru.nsu.g.beryanov.dto.TagDTO;

import java.util.List;

public interface TagDAO {
    List<TagDTO> getTags(long nodeId);

    void createInsertTag(TagDTO tag);
    void createInsertPreparedStatementTag(TagDTO tag);
    void createBatchPreparedStatementTags(List<TagDTO> tagDTOs);
}
