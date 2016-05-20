package com.cs165.domefavor.domefavor;

/**
 * Created by xuehanyu on 5/19/16.
 */
public class PriceItem {
    private double price;
    private String personID;

    public static final String priceS = "price";
    public static final String personS = "personID";

    public void setPrice(double price) { this.price = price; }

    public void setPersonID(String personID){ this.personID = personID; }

    public double getPrice() { return price; }

    public String getPersonID() { return personID; }
}
