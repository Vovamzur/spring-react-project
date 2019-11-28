package com.example.demo.dao.springData;

import com.example.demo.domain.Film;
import com.example.demo.domain.Ticket;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class TicketRepositoryCustomImpl implements TicketRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Ticket getFreeTicketByFilm(Film film) {
        TypedQuery<Ticket> query =  em.createQuery("select t from Ticket t where t.film.id=:id and t.client is null", Ticket.class);
        query.setMaxResults(1);
        query.setFirstResult(0);
        return query.setParameter("id", film.getId()).getSingleResult();
    }
}
