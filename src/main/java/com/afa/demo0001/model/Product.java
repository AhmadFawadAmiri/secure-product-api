package com.afa.demo0001.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "This can not be empty!")
    @Size(min = 2, message = "The name should be at least 2 Characters.")
    private String name;

    @Min(value = 0, message = "The price can not be negative!")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(){}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    //Getters and Setters

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
