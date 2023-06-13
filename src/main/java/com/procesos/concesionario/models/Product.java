package com.procesos.concesionario.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;
    private String category;
    @Column(name = "description", length = 10000)
    private String description;
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
}
