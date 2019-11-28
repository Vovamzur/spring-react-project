package com.example.demo.services;

//import com.example.demo.dao.jdbcTemplate.FilmDao;
import com.example.demo.dao.jpa.FilmDao;
import com.example.demo.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    @Autowired
    private FilmDao filmDao;

    public List<Film> getAll() {
        return filmDao.findAll();
    }

    public Optional<Film> findById(int id) {
        try {
            return Optional.of(filmDao.findFilmById(id));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }

    public Film createFilm(Film film) {
        return filmDao.createFilm(film);
    }
}
