package com.example.demo.services.springData;

import com.example.demo.dao.springData.ClientRepository;
import com.example.demo.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceSD {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> getAll() {
        List<Client> resultList = new ArrayList<Client>();
        clientRepository.findAll().forEach(resultList::add);

        return resultList;
    }

    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return this.clientRepository.save(client);
    }

    public boolean deleteClient(int id) {
        clientRepository.deleteById(id);
        return true;
    }

    public boolean updateClient(Client client, int id) {
        clientRepository.save(client);
        return true;
    }
}
