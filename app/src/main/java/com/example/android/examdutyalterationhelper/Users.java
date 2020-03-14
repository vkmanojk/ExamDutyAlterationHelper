package com.example.android.examdutyalterationhelper;

public class Users {

    public String name,phone,email;

    public Users() {
    }

    protected Users(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getPhone() {
        return phone;
    }

    protected void setPhone(String phone) {
        this.phone = phone;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }
}
