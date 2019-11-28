package com.example.demo.dao.springData;

import com.example.demo.domain.Client;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClientRepositoryCustomImpl implements ClientRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Client getClientByName(String name) {
        TypedQuery<Client> query =  em.createQuery("select c from Client c where c.name=:name", Client.class);
        List resultList = query.setParameter("name", name).getResultList();

        if(resultList.isEmpty()) {
            return null;
        }
        return (Client)resultList.get(0);
    }
}
