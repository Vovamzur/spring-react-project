package com.example.demo.services.springData;

import com.example.demo.dao.springData.FilmRepository;
import com.example.demo.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceSD {

    @Autowired
    FilmRepository filmRepository;

    public List<Film> getAll() {
        List<Film> resultList = new ArrayList<Film>();
        filmRepository.findAll().forEach(resultList::add);

        return resultList;
    }

    public Optional<Film> findById(int id) {
        return filmRepository.findById(id);
    }

    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    public boolean deleteFilmById(int id) {
        filmRepository.deleteById(id);
        return true;
    }

    public boolean updateFilmById(Film film, int id) {
        filmRepository.save(film);
        return true;
    }


}
