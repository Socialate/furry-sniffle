package com.socialteinc.socialate;

import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CheckEmailActivityTest {

    @Rule
    public ActivityTestRule<CheckEmailActivity> mActivityRule = new ActivityTestRule<>(
            CheckEmailActivity.class);

    /**
     * This is a test for Checking if the username has already being taken or not,
     * if username doesn't exist, you'll be allowed to create a password
    **/

    @Test
    public void EmailTest(){

        assertEquals(false, CheckEmailActivity.isValidEmail("mmmmm"));
        assertEquals(false, CheckEmailActivity.isValidEmail("abcd.com"));
        assertEquals(true, CheckEmailActivity.isValidEmail("socialate@gmail.com"));
        assertEquals(true, CheckEmailActivity.isValidEmail("1234@gmail.com"));
    }

    @Test
    public void InvalidEmailTest() throws InterruptedException {
        onView(withId(R.id.emailEditText)).perform(typeText("invalidemail.com"));
        onView(withId(R.id.nextButton)).perform(click());
    }

    @Test
    public void CheckEmailTest() throws Exception{
        //onView(withId(R.id.emailEditText)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        //onView(withId(R.id.nextButton)).perform(click());
        final String email1 = "joe@gmail.com";
        final String email2 = "john@gmail.com";
        Looper.prepare();
        final CheckEmailActivity obj = new CheckEmailActivity();
        obj.checkAccountEmailExistsInFirebase(email1);
        obj.checkAccountEmailExistsInFirebase(email2);

    }
}