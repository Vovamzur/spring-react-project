package com.example.demo.controller;

import com.example.demo.domain.Client;
import com.example.demo.services.ClientService;
import com.example.demo.services.springData.ClientServiceSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    ClientServiceSD clientService;

    @GetMapping("/")
    public List<Client> getAllClients() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getClientById(@PathVariable(name="id") int id) {
        return clientService.findById(id)
                .map( client -> ResponseEntity.ok(client))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return new ResponseEntity<Client>(createdClient, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable(name="id") int id) {
        boolean isRemoved =  clientService.deleteClient(id);

        if(isRemoved) {
            String result = "Ok";
            return new ResponseEntity(result, HttpStatus.OK);
        }

        String result = "Not Ok";
        return new ResponseEntity(result, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@RequestBody Client client, @PathVariable(name="id") int id) {
        boolean isUpdated = clientService.updateClient(client, id);

        if(isUpdated) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
