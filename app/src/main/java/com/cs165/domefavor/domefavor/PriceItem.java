package com.cs165.domefavor.domefavor;

/**
 * PriceItem class
 * Created by xuehanyu on 5/19/16.
 */
public class PriceItem {
    private double price;
    private String personID;
    private String age;
    private String gender;
    private String url;
    private String creditMoney;   //the credit used for rewards

    public static final String priceS = "price";
    public static final String personIDS = "personID";
    public static final String ageS = "age";
    public static final String genderS = "gender";
    public static final String urlS = "url";
    public static final String creditMoneyS = "credit";

    public PriceItem(double price, String personID, String age, String gender, String url){
        this.price = price;
        this.personID = personID;
        this.age = age;
        this.gender = gender;
        this.url = url;
    }


    public PriceItem(String age, String gender, String credit) {
        this.gender = gender;
        this.age = age;
        this.creditMoney = credit;
    }

    public void setPrice(double price) { this.price = price; }

    public void setPersonID(String personID){ this.personID = personID; }

    public void setGender(String gender) { this.gender = gender; }

    public void setAge(String age) { this.age = age; }

    public void setUrl(String url) { this.url = url; }

    public void setCreditMoney(String creditMoney) { this.creditMoney = creditMoney; }

    public String getUrl(){ return url; }

    public String getAge() { return age; }

    public String getGender() { return gender; }

    public double getPrice() { return price; }

    public String getPersonID() { return personID; }

    public String getCreditMoney() { return creditMoney; }
}
