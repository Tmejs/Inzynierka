package com.pjkurs.domain;

public class Discount extends DBObject {

    public Integer id;
    public Double discount_precentage;
    public String name;
    public String description;
    public Double money;

    public Discount() {
    }

    public Discount(Discount selectedDiscount) {
        this.id = selectedDiscount.id;
        this.discount_precentage = selectedDiscount.discount_precentage;
        this.name = selectedDiscount.name;
        this.description = selectedDiscount.description;
        this.money = selectedDiscount.money;
    }
}
