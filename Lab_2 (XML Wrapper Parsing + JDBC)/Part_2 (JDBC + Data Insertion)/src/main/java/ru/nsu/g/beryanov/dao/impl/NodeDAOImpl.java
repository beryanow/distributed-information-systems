package ru.nsu.g.beryanov.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import ru.nsu.g.beryanov.dao.NodeDAO;
import ru.nsu.g.beryanov.dto.NodeDTO;
import ru.nsu.g.beryanov.utility.SQLStatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@RequiredArgsConstructor
public class NodeDAOImpl implements NodeDAO {
    private final Connection connection;

    @SneakyThrows
    @Override
    public NodeDTO getNode(long nodeId) {
        PreparedStatement preparedStatement = connection.prepareStatement(SQLStatements.GET_NODE_EXPRESSION);
        preparedStatement.setLong(1, nodeId);

        ResultSet resultSet = preparedStatement.executeQuery(SQLStatements.GET_NODE_EXPRESSION);
        if (resultSet.next()) {
            return createNode(resultSet);
        }

        return null;
    }

    @SneakyThrows
    @Override
    public void insertNode(NodeDTO nodeDTO) {
        Statement statement = connection.createStatement();

        String sqlRequest = new StringBuilder().append("insert into nodes(id, version, timestamp, uid, username, changeset, latitude, longitude) ")
                .append("values (")
                .append(nodeDTO.getId())
                .append(", ").append(nodeDTO.getVersion())
                .append(", ").append("'").append(nodeDTO.getTimestamp()).append("'")
                .append(", ").append(nodeDTO.getUid())
                .append(", ").append("'").append(nodeDTO.getUsername().replaceAll("'", "''")).append("'")
                .append(", ").append(nodeDTO.getChangeset())
                .append(", ").append(nodeDTO.getLatitude())
                .append(", ").append(nodeDTO.getLongitude())
                .append(")").toString();

        statement.execute(sqlRequest);
    }

    @SneakyThrows
    @Override
    public void insertPreparedNode(NodeDTO nodeDTO) {
        PreparedStatement statement = connection.prepareStatement(SQLStatements.INSERT_NODE_EXPRESSION);
        prepareStatement(statement, nodeDTO);

        statement.execute();
    }

    @SneakyThrows
    @Override
    public void batchInsertNodes(List<NodeDTO> nodeDTOs) {
        PreparedStatement statement = connection.prepareStatement(SQLStatements.INSERT_NODE_EXPRESSION);
        for (NodeDTO nodeDTO : nodeDTOs) {
            prepareStatement(statement, nodeDTO);
            statement.addBatch();
        }

        statement.executeBatch();
    }

    @SneakyThrows
    private NodeDTO createNode(ResultSet resultSet) {
        return NodeDTO.builder()
                .id(resultSet.getLong("id"))
                .version(resultSet.getInt("version"))
                .timestamp(resultSet.getDate("timestamp"))
                .uid(resultSet.getLong("uid"))
                .username(resultSet.getString("username"))
                .latitude(resultSet.getDouble("latitude"))
                .longitude(resultSet.getDouble("longitude"))
                .build();
    }

    @SneakyThrows
    private void prepareStatement(PreparedStatement statement, NodeDTO nodeDTO) {
        statement.setLong(1, nodeDTO.getId());
        statement.setInt(2, nodeDTO.getVersion());
        statement.setDate(3, nodeDTO.getTimestamp());
        statement.setLong(4, nodeDTO.getUid());
        statement.setString(5, nodeDTO.getUsername());
        statement.setLong(6, nodeDTO.getChangeset());
        statement.setDouble(7, nodeDTO.getLatitude());
        statement.setDouble(8, nodeDTO.getLongitude());
    }
}
