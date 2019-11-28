package com.example.demo.dao.springData;

import com.example.demo.domain.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>, ClientRepositoryCustom {
}
