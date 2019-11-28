package com.example.demo.dao.jpa;

import com.example.demo.dao.TicketDaoI;
import com.example.demo.domain.Film;
import com.example.demo.domain.Ticket;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class TicketDao implements TicketDaoI {

    @PersistenceContext
    EntityManager em;

    public TicketDao() {}

    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return em.createQuery("select t from Ticket t").getResultList();
    }

    public Ticket getTicketById(int id) {
        TypedQuery<Ticket> query =  em.createQuery("select t from Ticket t where t.id=:id", Ticket.class);
        return query.setParameter("id", id).getSingleResult();
    }

    public Ticket getFreeTicketByFilm(Film film) {
        TypedQuery<Ticket> query =  em.createQuery("select t from Ticket t where t.film.id=:id and t.client is null", Ticket.class);
        query.setMaxResults(1);
        query.setFirstResult(0);
        return query.setParameter("id", film.getId()).getSingleResult();
    }

    public boolean updateTicketById(Ticket ticket, int id) {
        em.merge(ticket);
        return true;
    }
}
