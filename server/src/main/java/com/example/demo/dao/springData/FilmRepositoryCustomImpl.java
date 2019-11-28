package com.example.demo.dao.springData;

import com.example.demo.domain.Client;
import com.example.demo.domain.Film;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class FilmRepositoryCustomImpl implements FilmRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Film getFilmByName(String name) {
        TypedQuery<Film> query =  em.createQuery("select f from Film f where f.name=:name", Film.class);
        List resultList = query.setParameter("name", name).getResultList();

        if(resultList.isEmpty()) {
            return null;
        }
        return (Film)resultList.get(0);
    }
}
