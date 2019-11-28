package com.example.demo.dao;

import com.example.demo.domain.Client;

import java.util.List;

public interface ClientDaoI {

    List<Client> findAll();
    Client findClientById(int id);
    Client getClientByName(String name);
    Client createClient(Client client);
    boolean deleteClientById(int id);
    boolean updateClientById(Client client, int id);
}
