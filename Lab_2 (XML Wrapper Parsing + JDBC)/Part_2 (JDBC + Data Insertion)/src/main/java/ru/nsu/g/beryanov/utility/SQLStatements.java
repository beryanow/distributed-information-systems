package ru.nsu.g.beryanov.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SQLStatements {
    public String GET_NODE_EXPRESSION = new StringBuilder()
            .append("select id, version, timestamp, uid, username, changeset, latitude, longitude ")
            .append("from nodes ")
            .append("where id = ?")
            .toString();

    public String CREATE_NODE_EXPRESSION = new StringBuilder()
            .append("insert into nodes(id, version, timestamp, uid, username, changeset, latitude, longitude) ")
            .append("values (?, ?, ?, ?, ?, ?, ?, ?)")
            .toString();

    public String GET_TAG_EXPRESSION = new StringBuilder()
            .append("select node_id, key, value ")
            .append("from tags ")
            .append("where node_id = ?")
            .toString();

    public String CREATE_TAG_EXPRESSION = new StringBuilder()
            .append("insert into tags(node_id, key, value) ")
            .append("values (?, ?, ?)")
            .toString();
}
