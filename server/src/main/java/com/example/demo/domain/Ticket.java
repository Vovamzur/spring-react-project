package com.example.demo.domain;

import java.util.Date;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name="client")
    private Client client;
    private Date dateOfSale;
    @ManyToOne()
    @JoinColumn(name="film")
    private Film film;
    private float price;
    private int place;
}
