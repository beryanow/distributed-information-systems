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
    private final Connection connection;

    @SneakyThrows
    @Override
    public List<TagDTO> getTags(long nodeId) {
        PreparedStatement statement = connection.prepareStatement(SQLStatements.GET_TAG_EXPRESSION);
        statement.setLong(1, nodeId);

        ResultSet resultSet = statement.executeQuery(SQLStatements.GET_TAG_EXPRESSION);
        List<TagDTO> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(createTag(resultSet));
        }

        return tags;
    }

    @SneakyThrows
    @Override
    public void insertTag(TagDTO tag) {
        Statement statement = connection.createStatement();
        String sqlRequest = new StringBuilder().append("insert into tags(node_id, key, value) ")
                .append("values (")
                .append(tag.getNodeId())
                .append(", ").append("'").append(tag.getKey().replaceAll("'", "''")).append("'")
                .append(", ").append("'").append(tag.getValue().replaceAll("'", "''")).append("'")
                .append(")").toString();

        statement.execute(sqlRequest);
    }

    @SneakyThrows
    @Override
    public void insertPreparedTag(TagDTO tag) {
        PreparedStatement statement = connection.prepareStatement(SQLStatements.INSERT_TAG_EXPRESSION);
        prepareStatement(statement, tag);

        statement.execute();
    }

    @SneakyThrows
    @Override
    public void batchInsertTags(List<TagDTO> tagDTOs) {
        PreparedStatement statement = connection.prepareStatement(SQLStatements.INSERT_TAG_EXPRESSION);
        for (TagDTO tag : tagDTOs) {
            prepareStatement(statement, tag);
            statement.addBatch();
        }

        statement.executeBatch();
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
