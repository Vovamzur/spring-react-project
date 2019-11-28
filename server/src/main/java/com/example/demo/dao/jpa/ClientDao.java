package com.example.demo.dao.jpa;

import com.example.demo.dao.ClientDaoI;
import com.example.demo.domain.Client;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Transactional
@Repository
public class ClientDao implements ClientDaoI {

    @PersistenceContext
    EntityManager em;

    public ClientDao() {}

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return em.createQuery("select c from Client c").getResultList();
    }

    public Client findClientById(int id) {
        TypedQuery<Client> query =  em.createQuery("select c from Client c where c.id=:id", Client.class);
        return query.setParameter("id", id).getSingleResult();
    }

    public Client getClientByName(String name) {
        TypedQuery<Client> query =  em.createQuery("select c from Client c where c.name=:name", Client.class);
        List resultList = query.setParameter("name", name).getResultList();

        if(resultList.isEmpty()) {
            return null;
        }
        return (Client)resultList.get(0);
    }

    public Client createClient(Client client) {
        em.persist(client);

        return client;
    }

    public boolean deleteClientById(int id) {
        Client client = findClientById(id);
        em.remove(client);
        return true;
    }

    public boolean updateClientById(Client client, int id) {
        em.merge(client);
        return true;
    };
}
