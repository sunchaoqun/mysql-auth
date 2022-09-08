package org.ab2.ab2change.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepoistory implements UserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ArrayNode getUsers() {

        ArrayNode changes = objectMapper.createArrayNode();

        String sql = "select host, user from mysql.user";
        jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    ArrayNode values = objectMapper.createArrayNode();
                    values.add(rs.getString("host"));
                    values.add(rs.getString("user"));
                    changes.add(values);
                    return null;
                });
        return changes;
    }
}
