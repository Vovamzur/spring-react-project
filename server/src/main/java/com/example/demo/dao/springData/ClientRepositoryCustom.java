package com.example.demo.dao.springData;

import com.example.demo.domain.Client;

public interface ClientRepositoryCustom {
    Client getClientByName(String name);
}
