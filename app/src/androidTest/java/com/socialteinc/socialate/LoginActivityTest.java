package com.socialteinc.socialate;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

//    @Before
//    public void startActivity(){
//        main.getActivity();
//    }
//
//    @Test
//    public void isDisplayedTest() throws InterruptedException{
//        Thread.sleep(2000);
//        //main.getActivity();
//        onView(withId(R.id.email_field)).check(matches(isDisplayed()));
//        onView(withId(R.id.password_field)).check(matches(isDisplayed()));
//        onView(withId(R.id.SinginButton)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.appNameTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.orTextView)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.resetPasswordTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.creatAccountTextView)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.facebookButton)).check(matches(isDisplayed()));
//        onView(withId(R.id.googleButton)).check(matches(isDisplayed()));
//
//        Thread.sleep(2000);
//
//    }
//
    @Test
    public void noValidEmailTest() {
        onView(withId(R.id.email_field)).perform(typeText("invalidsocialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
    }

    @Test
    public void noValidPasswordTest(){
        onView(withId(R.id.email_field)).perform(typeText("invalid@socialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(clearText());
        onView(withId(R.id.SinginButton)).perform(click());
    }
//
//    @Test
//    public void loginTesting() throws InterruptedException {
//        Thread.sleep(2000);
//        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
//        onView(withId(R.id.SinginButton)).perform(click());
//        Thread.sleep(2000);
//    }
//
//    @Test
//    public void signUpTesting() throws InterruptedException {
//        init();
//        Thread.sleep(3000);
//        onView(withId(R.id.creatAccountTextView)).perform(click());
//        intended(hasComponent(RegisterActivity.class.getName()));
//        release();
//        //Thread.sleep(3000);
//    }
//
//    @Test
//    public void resetPasswordTesting() throws InterruptedException {
//        init();
//        Thread.sleep(3000);
//        onView(withId(R.id.resetPasswordTextView)).perform(click());
//        intended(hasComponent(ResetPasswordActivity.class.getName()));
//        release();
//
//    }

}
