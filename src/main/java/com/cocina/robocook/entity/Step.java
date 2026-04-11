package com.cocina.robocook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "step")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "description")
    private String description;

    //constructor
    public Step(int orderNumber, String description) {
        this.orderNumber = orderNumber;
        this.description = description;
    }
}
