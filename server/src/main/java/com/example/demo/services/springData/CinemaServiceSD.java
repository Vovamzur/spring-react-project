package com.example.demo.services.springData;

import com.example.demo.dao.springData.ClientRepository;
import com.example.demo.dao.springData.FilmRepository;
import com.example.demo.dao.springData.TicketRepository;
import com.example.demo.domain.Client;
import com.example.demo.domain.Film;
import com.example.demo.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CinemaServiceSD {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    TicketRepository ticketRepository;

    public String registerClient(String name, String password, int age) {
        Client client = clientRepository.getClientByName(name);
        if(client != null) {
            return "Client with name " + name + " already exists";
        }
        Client createdClient = clientRepository.save(Client.builder().name(name).pass(password).age(age).build());
        if(createdClient != null) {
            return "Client was registered";
        };

        return "Client can not be registered";
    }

    public String sellTicket(String clientName, String filmName) {
        Client client = clientRepository.getClientByName(clientName);
        if(client == null) {
            return "Client with such name does not exist";
        }
        Film film = filmRepository.getFilmByName(filmName);
        if(film == null) {
            return "Film with such name does not exist";
        }
        Ticket ticket = ticketRepository.getFreeTicketByFilm(film);
        if(ticket == null) {
            return "There are no free tickets for this film";
        }
        int ticketId = ticket.getId();
        ticketRepository.save(new Ticket(ticketId, client, new Date(), film, ticket.getPrice(), ticket.getPlace()));
        return "Ticket was booked by " + clientName;
    }
}
