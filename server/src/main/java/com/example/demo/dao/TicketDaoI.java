package com.example.demo.dao;

import com.example.demo.domain.Film;
import com.example.demo.domain.Ticket;

import java.util.List;

public interface TicketDaoI {

    List<Ticket> findAll();
    Ticket getTicketById(int id);
    Ticket getFreeTicketByFilm(Film film);
    boolean updateTicketById(Ticket ticket, int id);

}
