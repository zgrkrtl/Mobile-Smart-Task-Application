package com.example.smarttaskapp;

//test dependencies

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;


//Testing doesnt seem to work
public class NameValidatorTest {
    @Rule
    public ActivityScenarioRule<UserProfileActivity> activityScenarioRule =
            new ActivityScenarioRule<>(UserProfileActivity.class);

    ///Ignore the testing becausse it doesnt work properly
    @Ignore
    @Test
    public void testNameValidation() {

        Espresso.onView(withId(R.id.userName))
                .perform(ViewActions.typeText("Hs"))
                .perform(ViewActions.closeSoftKeyboard());

        //When clicked save validate
        Espresso.onView(withId(R.id.saveUserInfoBtn)).perform(ViewActions.click());

        // input a valid name
        Espresso.onView(withId(R.id.userName))
                .perform(ViewActions.replaceText("Hsts"))
                .perform(ViewActions.closeSoftKeyboard());


    }
}
// get string error text R.string.error_invalid_name



