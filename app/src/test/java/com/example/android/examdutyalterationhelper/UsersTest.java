package com.example.android.examdutyalterationhelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;


public class UsersTest {

    static int i = 1;

    @Before
    public void setUp(){
        System.out.println("Testcase: "+i+" inititiated");
        i+=1;
    }

    @Test
    public void classcheck() {

        try

        {
            Class.forName("com.example.android.examdutyalterationhelper.Users");
        } catch(ClassNotFoundException e)
        {
//            AssertionError();
            fail("Class Not Found !");
        }
    }

    @Test
    public void getNamecheck() {
        Users obj = new Users("name","1234567890","name@email.com");
        String temp = obj.getName();
        assertEquals("Test Case Failed","name", temp);
    }

    @Test
    public void getPhonecheck() {
        Users obj = new Users("name","1234567890","name@email.com");
        String temp = obj.getPhone();
        assertEquals("Test Case Failed","1234567890", temp);
    }

    @Test
    public void getEmailcheck() {
        Users obj = new Users("name","1234567890","name@email.com");
        String temp = obj.getEmail();
        assertEquals("Test Case Failed","name@email.com", temp);
    }

    @Test
    public void setEmailcheck() {
        Users obj = new Users("name","1234567890","@email.com");
        obj.setEmail("name@email.com");
        String temp = obj.getEmail();
        assertEquals("Test Case Failed","name@email.com", temp);
    }

    @After
    public void tearDown(){
        System.out.println("Testcase: "+(i-1)+" compiled ");
    }

}