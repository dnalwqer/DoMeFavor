package com.cs165.domefavor.domefavor.backend;

/**
 * Created by Alex on 16/5/19.
 */
public class Profile {
    public static final String Profile_PARENT_ENTITY_NAME = "ProfileParent";
    public static final String Profile_PARENT_KEY_NAME = "ProfileParent";

    public static final String Profile_ENTITY_NAME = "Profile";
    public static final String FIELD_NAME_age = "age";
    public static final String FIELD_NAME_email = "email";
    public static final String FIELD_NAME_gender = "gender";
    public static final String FIELD_NAME_url = "url";
    public static final String KEY_NAME = FIELD_NAME_email;
    public static final String FIELD_NAME_credit = "credit";

    public String age;
    public String email;
    public String gender;
    public String url;
    public String credit;

    public Profile(String age, String email, String gender, String url) {
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.url = url;
        credit = "0";
    }

    //@han
    public Profile(String age, String email, String gender, String url, String credit) {
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.url = url;
        this.credit = credit;
    }
}
