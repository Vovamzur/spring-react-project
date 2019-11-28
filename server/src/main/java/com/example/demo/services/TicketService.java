package com.example.demo.services;

//import com.example.demo.dao.jdbcTemplate.TicketDao;
import com.example.demo.dao.jpa.TicketDao;
import com.example.demo.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    public List<Ticket> getAll() {
        return ticketDao.findAll();
    }

    public Optional<Ticket> findById(int id) {
        try {
            return Optional.of(ticketDao.getTicketById(id));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }
}
