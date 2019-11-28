package com.example.demo.dao.jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.ClientDaoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.Client;

//@Repository
public class ClientDao implements ClientDaoI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Client> findAll() {
        return this.jdbcTemplate.query("select id, name, pass, age from client", ClientDao::mapRow);
    }

    private static Client mapRow(ResultSet resultSet, int i) throws SQLException {
        return Client
                .builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .pass(resultSet.getString("pass"))
                .age(resultSet.getInt("age"))
                .build();
    }

    public Client findClientById(int id) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "select id, name, pass, age from client where id = ?",
                    new Object[]{id},
                    ClientDao::mapRow);
        } catch(EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Client getClientByName(String name) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "select id, name, pass, age from client where name = ?",
                    new Object[]{name},
                    ClientDao::mapRow);
        } catch(EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Client createClient(Client client) {
        final String sql = "insert into client (name, pass, age) values (?, ?, ?)";

        int row = this.jdbcTemplate.update(sql, client.getName(), client.getPass(), client.getAge());
        return row > 0 ? client : null;
    }

    public boolean deleteClientById(int id) {
        final String sql = "delete from client where id = ?";

        return this.jdbcTemplate.update(sql, id) > 0;
    }

    public boolean updateClientById(Client client, int id) {
        final String sql = "update client set name=?, pass=?, age=? where id =?";

        return this.jdbcTemplate.update(sql, client.getName(), client.getPass(), client.getAge(), id) > 0;
    }
}
