package com.example.android.examdutyalterationhelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    static int i = 1;

    @Before
    public void setUp() throws Exception {
        System.out.println("Testcase: "+i+" inititiated");
        i+=1;
    }

    @Test
    public void classcheck() {

        try {
            Class.forName("com.example.android.examdutyalterationhelper.MainActivity");
        }
        catch(ClassNotFoundException e) {
            fail("Class Not Found !");
        }
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Testcase: "+(i-1)+" compiled ");
    }
}