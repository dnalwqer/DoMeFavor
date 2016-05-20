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
    public static final String KEY_NAME = FIELD_NAME_email;

    public String age;
    public String email;
    public String gender;

    public Profile(String age, String email, String gender) {
        this.gender = gender;
        this.email = email;
        this.age = age;
    }
}
