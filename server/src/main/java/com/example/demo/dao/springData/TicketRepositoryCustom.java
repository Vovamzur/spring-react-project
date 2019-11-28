package com.example.demo.dao.springData;

import com.example.demo.domain.Film;
import com.example.demo.domain.Ticket;

public interface TicketRepositoryCustom {
    Ticket getFreeTicketByFilm(Film film);
}
