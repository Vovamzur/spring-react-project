package com.example.demo.dao.jdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.TicketDaoI;
import com.example.demo.domain.Client;
import com.example.demo.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.Ticket;

//@Repository
public class TicketDao implements TicketDaoI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static FilmDao filmDao;
    private static ClientDao clientDao;

    @Autowired
    public void setFilmDao(FilmDao filmDao) {
        TicketDao.filmDao = filmDao;
    }
    @Autowired
    public void setClientDaoDao(ClientDao clientDao) {
        TicketDao.clientDao = clientDao;
    }

    public List<Ticket> findAll() {
        return this.jdbcTemplate.query(
                "select id, client, dateOfSale, film, price, place from ticket", TicketDao::mapRow);
    }

    private static Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        int filmId = resultSet.getInt("film");
        int clientId = resultSet.getInt("client");
        Film film = filmId == 0 ? null : TicketDao.filmDao.findFilmById(filmId);
        Client client = clientId == 0 ? null : TicketDao.clientDao.findClientById(clientId);

        return Ticket
                .builder()
                .id(resultSet.getInt("id"))
                .client(client)
                .dateOfSale(resultSet.getDate("dateOfSale"))
                .film(film)
                .price(resultSet.getFloat("price"))
                .place(resultSet.getInt("place"))
                .build();
    }

    public Ticket getTicketById(int id) {
        return this.jdbcTemplate.queryForObject(
                "select id, client, dateOfSale, film, price, place from ticket where id = ?",
                new Object[]{id},
                TicketDao::mapRow);
    }

    public Ticket getFreeTicketByFilm(Film film) {
        final String sql = "SELECT * FROM ticket WHERE film = " + film.getId() + " AND client IS NULL LIMIT 1";

        return this.jdbcTemplate.queryForObject(sql, TicketDao::mapRow);
    }

    public boolean updateTicketById(Ticket ticket, int id) {
        final String sql = "update ticket set client=?, dateOfSale=?, film=?, price=?, place=? where id =?";

        return this.jdbcTemplate.update(
                sql,
                ticket.getClient().getId(),
                ticket.getDateOfSale(),
                ticket.getFilm().getId(),
                ticket.getPrice(),
                ticket.getPlace(),
                id
        ) > 0;
    }
}
