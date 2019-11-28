package com.example.demo.dao.springData;

import com.example.demo.domain.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends CrudRepository<Film, Integer>, FilmRepositoryCustom {
}
