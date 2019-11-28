package com.example.demo.controller;

import com.example.demo.domain.Client;
import com.example.demo.services.CinemaService;
import com.example.demo.services.springData.CinemaServiceSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    @Autowired
    CinemaServiceSD cinemaService;

    @PostMapping("/{clientName}/{filmName}")
    public ResponseEntity<String> bookTicket(
            @PathVariable(name="clientName") String clientName,
            @PathVariable(name="filmName") String filmName
    ) {
        return ResponseEntity.ok(this.cinemaService.sellTicket(clientName, filmName));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody Client client) {
        String result = this.cinemaService.registerClient(
                client.getName(),
                client.getPass(),
                client.getAge());
        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }
}
