package com.example.demo.dao.jpa;

import com.example.demo.dao.FilmDaoI;
import com.example.demo.domain.Film;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class FilmDao implements FilmDaoI {

    @PersistenceContext
    EntityManager em;

    public FilmDao() {}

    @Transactional(readOnly = true)
    public List<Film> findAll() {
        return em.createQuery("select f from Film f").getResultList();
    }

    public Film findFilmById(int id) {
        TypedQuery<Film> query =  em.createQuery("select f from Film f where f.id=:id", Film.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Transactional
    public Film getFilmByName(String name) {
        TypedQuery<Film> query =  em.createQuery("select f from Film f where f.name=:name", Film.class);
        List resultList = query.setParameter("name", name).getResultList();

        if(resultList.isEmpty()) {
            return null;
        }
        return (Film)resultList.get(0);
    }

    public Film createFilm(Film film) {
        em.persist(film);
        return film;
    }
}
