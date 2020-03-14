package com.example.android.examdutyalterationhelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignUpActivityTest {

    static int i = 1;

    @Before
    public void setUp(){
        System.out.println("Testcase: "+i+" inititiated");
        i+=1;
    }

    @Test
    public void classcheck() {

        try {
            Class.forName("com.example.android.examdutyalterationhelper.SignUpActivity");
        }
        catch(ClassNotFoundException e) {
            fail("Class Not Found !");
        }
    }

    @Test
    public void Emailcheck1 () {
        assertTrue(SignUpActivity.EmailCheck("name@mail.com"));
    }

    @Test
    public void Emailcheck2 () {
        assertTrue(SignUpActivity.EmailCheck("name@email.co.in"));
    }

    @Test
    public void Emailcheck3 () {
        assertFalse(SignUpActivity.EmailCheck("name@email"));
    }

    @Test
    public void Emailcheck4() {
        assertFalse(SignUpActivity.EmailCheck("@email.com"));
    }

    @Test
    public void Emailcheck5() {
        assertFalse(SignUpActivity.EmailCheck("name.com"));
    }
    @Test
    public void Emailcheck6() {
        assertFalse(SignUpActivity.EmailCheck("name."));
    }
    @Test
    public void Emailcheck7() {
        assertFalse(SignUpActivity.EmailCheck(""));
    }


    @Test
    public void Namecheck1() {
        assertTrue(SignUpActivity.NameCheck("name"));
    }

    @Test
    public void Namecheck2() {
        assertFalse(SignUpActivity.NameCheck("name123"));
    }

    @Test
    public void Namecheck3() {
        assertFalse(SignUpActivity.NameCheck("123"));
    }

    @Test
    public void Namecheck4() {
        assertFalse(SignUpActivity.NameCheck(""));
    }

    @Test
    public void Namecheck5() {
        assertFalse(SignUpActivity.NameCheck(" "));
    }

    @Test
    public void Phonecheck1() {
        assertFalse(SignUpActivity.PhoneCheck("123"));
    }

    @Test
    public void Phonecheck2() {
        assertFalse(SignUpActivity.PhoneCheck(""));
    }

    @Test
    public void Phonecheck3() {
        assertTrue(SignUpActivity.PhoneCheck("1234567890"));
    }

    @Test
    public void Phonecheck4() {
        assertFalse(SignUpActivity.PhoneCheck("name"));
    }

    @Test
    public void Phonecheck5() {
        assertFalse(SignUpActivity.PhoneCheck("123name"));
    }

    @Test
    public void Pwdcheck1() {
        assertFalse(SignUpActivity.PwdCheck(""));
    }

    @Test
    public void Pwdcheck2() {
        assertFalse(SignUpActivity.PwdCheck("123"));
    }

    @Test
    public void Pwdcheck3() {
        assertTrue(SignUpActivity.PwdCheck("abc123"));
    }

    @After
    public void tearDown(){
        System.out.println("Testcase: "+(i-1)+" compiled ");
    }
}