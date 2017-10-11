//package com.socialteinc.socialate;
//
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.intent.Intents.init;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.Intents.release;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//
//public class LoginActivityTest {
//
//    @Rule
//    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);
//
//    @Test
//    public void noValidEmailTest() throws InterruptedException {
//        Thread.sleep(1500);
//        onView(withId(R.id.email_field)).perform(typeText("invalidsocialate.com"), closeSoftKeyboard());
//        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
//        onView(withId(R.id.SinginButton)).perform(click());
//    }
//
//    @Test
//    public void noValidPasswordTest() throws InterruptedException {
//        Thread.sleep(1500);
//        onView(withId(R.id.email_field)).perform(typeText("invalid@socialate.com"), closeSoftKeyboard());
//        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
//        onView(withId(R.id.password_field)).perform(clearText());
//        onView(withId(R.id.SinginButton)).perform(click());
//    }
//
//    @Test
//    public void loginTesting() throws InterruptedException {
//        Thread.sleep(1500);
//        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
//        onView(withId(R.id.SinginButton)).perform(click());
//    }
//
//    @Test
//    public void signUpTesting() throws InterruptedException {
//        init();
//        Thread.sleep(1500);
//        onView(withId(R.id.creatAccountTextView)).perform(click());
//        intended(hasComponent(RegisterActivity.class.getName()));
//        release();
//    }
//
//    @Test
//    public void resetPasswordTesting() throws InterruptedException {
//        init();
//        Thread.sleep(1500);
//        onView(withId(R.id.resetPasswordTextView)).perform(click());
//        intended(hasComponent(ResetPasswordActivity.class.getName()));
//        release();
//
//    }
//
//}
