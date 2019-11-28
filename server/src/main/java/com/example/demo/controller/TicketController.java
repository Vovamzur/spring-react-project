package com.example.demo.controller;

import com.example.demo.domain.Ticket;
import com.example.demo.services.TicketService;
import com.example.demo.services.springData.TicketServiceSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    TicketServiceSD ticketService;

    @GetMapping("/")
    public List<Ticket> getAllClients() {
        return ticketService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getClientById(@PathVariable(name="id") int id) {
        return ticketService.findById(id)
                .map( ticket -> ResponseEntity.ok(ticket))
                .orElse(ResponseEntity.notFound().build());
    }
}

