package com.example.demo.dao;

import com.example.demo.domain.Film;

import java.util.List;

public interface FilmDaoI {

    List<Film> findAll();
    Film findFilmById(int id);
    Film getFilmByName(String name);
    Film createFilm(Film film);
}
