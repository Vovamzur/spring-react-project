package com.example.demo.controller;

import com.example.demo.domain.Film;
import com.example.demo.services.FilmService;
import com.example.demo.services.springData.FilmServiceSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    FilmServiceSD filmService;

    @GetMapping("/")
    public List<Film> getAllFilms() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getFilmById(@PathVariable(name="id") int id) {
        return filmService.findById(id)
                .map( film -> ResponseEntity.ok(film))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        Film createdFilm = filmService.createFilm(film);
        return new ResponseEntity<Film>(createdFilm, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFilm(@PathVariable(name="id") int id) {
        boolean removed = this.filmService.deleteFilmById(id);

        if(removed) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFilm(@RequestBody Film film, @PathVariable(name="id") int id) {
        boolean updated = this.filmService.updateFilmById(film, id);

        if(updated) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}

