package com.socialteinc.socialate;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddEntertainment() throws InterruptedException {
        login();
        Thread.sleep(5000);
        init();
        onView(withId(R.id.action_add_entertainment)).perform(click());
        intended(hasComponent(AddEntertainmentActivity.class.getName()));
        Intents.release();
        onView(withId(R.id.entertainmentTitleEditText)).perform(closeSoftKeyboard());
        onView(isRoot()).perform(ViewActions.pressBack());
        logout();
    }

    @Test
    public void testLogout() throws InterruptedException {
        login();
        Thread.sleep(5000);
        Intents.init();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
        Intents.release();
    }

    public void login(){
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
    }

    public void logout(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
    }
}
