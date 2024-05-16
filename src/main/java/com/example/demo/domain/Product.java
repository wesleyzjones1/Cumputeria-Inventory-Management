package com.example.demo.domain;

import com.example.demo.validators.ValidEnufParts;
import com.example.demo.validators.ValidProductPrice;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 *
 *
 *
 */
@Entity
@Table(name="Products")
@ValidProductPrice
@ValidEnufParts
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    @Min(value = 0, message = "Price value must be positive")
    double price;
    @Min(value = 0, message = "Inventory value must be positive")
    int inv;
    @Positive(message = "Store number must be positive")
    private Integer storeNumber;
    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "products", fetch = FetchType.EAGER)
    Set<Part> parts = new HashSet<>();

    @Column(name = "date_added")
    private LocalDate dateAdded;

    public Product() {
    }

    public Product(String name, double price, int inv) {
        this.name = name;
        this.price = price;
        this.inv = inv;
    }

    public Product(long id, String name, double price, int inv, String dateAdded, int storeNumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.dateAdded = LocalDate.parse(dateAdded, formatter);
        this.storeNumber = storeNumber;
    }

    public Integer getStoreNumber() { return storeNumber; }
    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void setDate(String dateAdded) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.dateAdded = LocalDate.parse(dateAdded, formatter);
    }

    public String getDate() {
        if (this.dateAdded != null) {
            return this.dateAdded.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
        this.inv = inv;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public String toString(){
        return this.name;
    }

    public Set<Part> getAssociatedParts() {
        return this.parts;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
