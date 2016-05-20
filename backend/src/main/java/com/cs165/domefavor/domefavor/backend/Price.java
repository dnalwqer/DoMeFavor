package com.cs165.domefavor.domefavor.backend;

/**
 * Created by Alex on 16/5/19.
 */
public class Price {
    public static final String Price_PARENT_ENTITY_NAME = "PriceParent";
    public static final String Price_PARENT_KEY_NAME = "PriceParent";

    public static final String Price_ENTITY_NAME = "Price";
    public static final String FIELD_NAME_price = "price";
    public static final String FIELD_NAME_id = "id";
    public static final String FIELD_NAME_taker = "taker";
    public static final String KEY_NAME = FIELD_NAME_id;

    public String price;
    public String id;
    public String taker;

    public Price(String price, String id, String taker) {
        this.price = price;
        this.id = id;
        this.taker = taker;
    }
}
