package com.example.demo.services.springData;

import com.example.demo.dao.springData.TicketRepository;
import com.example.demo.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceSD {

    @Autowired
    TicketRepository ticketRepository;

    public List<Ticket> getAll() {
        List<Ticket> resultList = new ArrayList<Ticket>();
        ticketRepository.findAll().forEach(resultList::add);

        return resultList;
    }

    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }
}
