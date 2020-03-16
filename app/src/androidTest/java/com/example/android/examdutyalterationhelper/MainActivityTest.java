package com.example.android.examdutyalterationhelper;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    String username = "sudharshanbasketball@gmail.com";
    String pwd = "aaaaaa";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void loginActivitySimulation(){
        Espresso.onView(withId(R.id.email_signin)).perform(typeText(username),closeSoftKeyboard());
        Espresso.onView(withId(R.id.pwd_signin)).perform(typeText(pwd),closeSoftKeyboard());
        Espresso.onView(withId(R.id.float_button_signin)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}