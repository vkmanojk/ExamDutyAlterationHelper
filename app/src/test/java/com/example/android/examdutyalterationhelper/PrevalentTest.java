package com.example.android.examdutyalterationhelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrevalentTest {

    static int i = 1;

    @Before
    public void setUp() throws Exception {
        System.out.println("Testcase: "+i+" inititiated");
        i+=1;
    }

    @Test
    public void classcheck() {

        try {
            Class.forName("com.example.android.examdutyalterationhelper.Prevalent");
        }
        catch(ClassNotFoundException e) {
            fail("Class Not Found !");
        }
    }

    @Test
    public void Usercheck() {
        Users obj = new Users("name","1234567890","name@email.com");
        Prevalent.currentOnlineUser = obj;
        String temp = Prevalent.currentOnlineUser.getEmail();
        assertEquals("name@email.com",temp);
    }

    @Test
    public void Emailcheck() {
        Prevalent.UserEmail = "name@mail.com";
        assertEquals("name@mail.com",Prevalent.UserEmail);
    }

    @Test
    public void Pwdcheck() {
        Prevalent.UserPasswordKey = "password";
        assertEquals("password", Prevalent.UserPasswordKey);

    }

    @Test
    public void Phcheck() {
        Prevalent.UserPhoneKey = "1234567890";
        assertEquals("1234567890", Prevalent.UserPhoneKey);

    }
        @After
    public void tearDown() throws Exception {
            System.out.println("Testcase: "+(i-1)+" compiled ");
    }
}