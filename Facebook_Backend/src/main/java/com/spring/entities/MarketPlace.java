package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "market_place")
@Getter
@Setter
public class MarketPlace implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "location_id")
    private Integer locationId;
    @Column(name = "price")
    private Integer price;
}
