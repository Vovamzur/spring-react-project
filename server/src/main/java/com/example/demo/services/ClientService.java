package com.example.demo.services;

//import com.example.demo.dao.jdbcTemplate.ClientDao;
import  com.example.demo.dao.jpa.ClientDao;
import com.example.demo.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientDao clientDao;

    public List<Client> getAll() {
        return clientDao.findAll();
    }

    public Optional<Client> findById(int id) {
        try {
            return Optional.of(clientDao.findClientById(id));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }

    public Client createClient(Client client) {
        return this.clientDao.createClient(client);
    }

    public boolean deleteClient(int id) {
        return clientDao.deleteClientById(id);
    }

    public boolean updateClient(Client client, int id) {
        return this.clientDao.updateClientById(client, id);
    }
}
