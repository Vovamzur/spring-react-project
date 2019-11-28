package com.example.demo.dao.springData;

import com.example.demo.domain.Film;

import java.util.List;

public interface FilmRepositoryCustom {
    Film getFilmByName(String name);
}
