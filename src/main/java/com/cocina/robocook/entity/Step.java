package com.cocina.robocook.entity;

import jakarta.persistence.*;

import java.util.Objects;

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
    public Step(){};

    public Step(int orderNumber, String description) {
        this.orderNumber = orderNumber;
        this.description = description;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Step step)) return false;
        return orderNumber == step.orderNumber && Objects.equals(id, step.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, description);
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
