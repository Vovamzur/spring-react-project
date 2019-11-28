package com.example.demo.services;

//import com.example.demo.dao.jdbcTemplate.ClientDao;
//import com.example.demo.dao.jdbcTemplate.FilmDao;
//import com.example.demo.dao.jdbcTemplate.TicketDao;
import com.example.demo.dao.jpa.ClientDao;
import com.example.demo.dao.jpa.FilmDao;
import com.example.demo.dao.jpa.TicketDao;


import com.example.demo.domain.Client;
import com.example.demo.domain.Film;
import com.example.demo.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CinemaService {

    @Autowired
    ClientDao clientDao;
    @Autowired
    FilmDao filmDao;
    @Autowired
    TicketDao ticketDao;

    public String registerClient(String name, String password, int age) {
        Client client = clientDao.getClientByName(name);
        if(client != null) {
            return "Client with name " + name + " already exists";
        }
        Client createdClient = clientDao.createClient(Client.builder().name(name).pass(password).age(age).build());
        if(createdClient != null) {
            return "Client was registered";
        };

        return "Client can not be registered";
    }

    public String sellTicket(String clientName, String filmName) {
        Client client = clientDao.getClientByName(clientName);
        if(client == null) {
            return "Client with such name does not exist";
        }
        Film film = filmDao.getFilmByName(filmName);
        if(film == null) {
            return "Film with such name does not exist";
        }
        Ticket ticket = ticketDao.getFreeTicketByFilm(film);
        if(ticket == null) {
            return "There are no free tickets for this film";
        }
        int ticketId = ticket.getId();
        boolean result = ticketDao.updateTicketById (
                new Ticket(ticketId, client, new Date(), film, ticket.getPrice(), ticket.getPlace()),
                ticketId);

        if(result) {
            return "Ticket was booked by " + clientName;
        }
        return "There are some problems";
    }
}
