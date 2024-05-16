package com.example.demo.domain;

import com.example.demo.validators.ValidDeletePart;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 *
 *
 */
@Entity
@ValidDeletePart
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="part_type",discriminatorType = DiscriminatorType.INTEGER)
@Table(name="Parts")
public class Part implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    @Min(value = 0, message = "Price value must be positive")
    double price;
    @Min(value = 0, message = "Inventory value must be positive")
    int inv;
    @Min(value = 0, message = "Minimum inventory value must be positive")
    int minInv;
    @Min(value = 0, message = "Maximum inventory value must be positive")
    int maxInv;
    @Min(value = 0, message = "Store Number must be positive")
    private Integer storeNumber = 0;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @ManyToMany
    @JoinTable(name="product_part", joinColumns = @JoinColumn(name="part_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    Set<Product> products= new HashSet<>();



    public Part() {
    }

    public Part(String name, double price, int inv) {
        this.name = name;
        this.price = price;
        this.inv = inv;
    }

    public Part(long id, String name, double price, int inv) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
    }

    public Part(long id, String name, double price, int inv, int minInv, int maxInv, String dateAdded, int storeNumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.minInv = minInv;
        this.maxInv = maxInv;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.dateAdded = LocalDate.parse(dateAdded, formatter);
        this.storeNumber = storeNumber;
    }

    public Integer getStoreNumber() { return storeNumber; }
    public void setStoreNumber(int storeNumber) {
        this.storeNumber = storeNumber;
    }

    // Getters and setters for minInv and maxInv
    public int getMinInventory() { return minInv; }

    public void setMinInventory(int minInv) { this.minInv = minInv; }

    public int getMaxInventory() { return maxInv; }

    public void setMaxInventory(int maxInv) { this.maxInv = maxInv; }

    public String getDate() {
        if (this.dateAdded != null) {
            return this.dateAdded.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
    }

    public void setDate(String dateAdded) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.dateAdded = LocalDate.parse(dateAdded, formatter);
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String toString(){
        return this.name;
    }

    //function to test if the inventory is between the minInventory and maxInventory
    public boolean isInventoryValid() {
        return this.getInv() >= this.getMinInventory() && this.getInv() <= this.getMaxInventory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Part part = (Part) o;

        return id == part.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
