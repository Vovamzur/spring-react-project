package com.example.demo.dao.springData;

import com.example.demo.domain.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer>, TicketRepositoryCustom {
}
