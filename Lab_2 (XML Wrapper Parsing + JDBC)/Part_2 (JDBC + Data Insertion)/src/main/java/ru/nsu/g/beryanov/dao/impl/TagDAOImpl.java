package ru.nsu.g.beryanov.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import ru.nsu.g.beryanov.dao.TagDAO;
import ru.nsu.g.beryanov.dto.TagDTO;
import ru.nsu.g.beryanov.utility.SQLStatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TagDAOImpl implements TagDAO {
    private final Statement insertStatement;
    private final PreparedStatement preparedGetTagStatement;
    private final PreparedStatement preparedCreateTagStatement;
    private final PreparedStatement preparedBatchCreateTagStatement;

    @SneakyThrows
    @Override
    public List<TagDTO> getTags(long nodeId) {
        preparedGetTagStatement.setLong(1, nodeId);

        ResultSet resultSet = preparedGetTagStatement.executeQuery(SQLStatements.GET_TAG_EXPRESSION);
        List<TagDTO> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(createTag(resultSet));
        }

        return tags;
    }

    @SneakyThrows
    @Override
    public void createInsertTag(TagDTO tag) {
        String sqlRequest = new StringBuilder().append("insert into tags(node_id, key, value) ")
                .append("values (")
                .append(tag.getNodeId())
                .append(", ").append("'").append(tag.getKey().replaceAll("'", "''")).append("'")
                .append(", ").append("'").append(tag.getValue().replaceAll("'", "''")).append("'")
                .append(")").toString();

        insertStatement.execute(sqlRequest);
    }

    @SneakyThrows
    @Override
    public void createInsertPreparedStatementTag(TagDTO tag) {
        prepareStatement(preparedCreateTagStatement, tag);

        preparedCreateTagStatement.execute();
    }

    @SneakyThrows
    @Override
    public void createBatchPreparedStatementTags(List<TagDTO> tagDTOs) {
        for (TagDTO tag : tagDTOs) {
            prepareStatement(preparedBatchCreateTagStatement, tag);
            preparedBatchCreateTagStatement.addBatch();
        }

        preparedBatchCreateTagStatement.executeBatch();
    }

    @SneakyThrows
    private static TagDTO createTag(ResultSet resultSet) {
        return TagDTO.builder()
                .nodeId(resultSet.getLong("node_id"))
                .key(resultSet.getString("key"))
                .value(resultSet.getString("value"))
                .build();
    }

    @SneakyThrows
    private void prepareStatement(PreparedStatement statement, TagDTO tag) {
        statement.setLong(1, tag.getNodeId());
        statement.setString(2, tag.getKey());
        statement.setString(3, tag.getValue());
    }
}
