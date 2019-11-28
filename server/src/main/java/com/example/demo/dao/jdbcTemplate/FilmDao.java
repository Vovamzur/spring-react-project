package com.example.demo.dao.jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.FilmDaoI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.Film;

//@Repository
public class FilmDao implements FilmDaoI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Film> findAll() {
        return this.jdbcTemplate.query(
                "select id, premierDate, name, genre from film" ,
                FilmDao::mapRow);
    }

    private static Film mapRow(ResultSet resultSet, int i) throws SQLException {
        return Film
                .builder()
                .id(resultSet.getInt("id"))
                .premiereDate(resultSet.getDate("premierDate"))
                .name(resultSet.getString("name"))
                .genre(resultSet.getString("genre"))
                .build();
    }

    public Film findFilmById(int id) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "select id, premierDate, name, genre from film where id = ?",
                    new Object[]{id},
                    FilmDao::mapRow);
        } catch(EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Film getFilmByName(String name) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "select id, premierDate, name, genre from film where name = ?",
                    new Object[] { name },
                    FilmDao::mapRow);
        } catch(EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Film createFilm(Film film) {
        final String sql = "insert into film (premierDate, name, genre) values (?, ?, ?)";

        int row = this.jdbcTemplate.update(sql, film.getPremiereDate(), film.getName(), film.getGenre());
        return row > 0 ? film : null;
    }

}
