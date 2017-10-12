package com.socialteinc.socialate;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    public void testAddEntertainment() throws InterruptedException {
//        login();
//        Thread.sleep(5000);
//        init();
//        onView(withId(R.id.action_add_entertainment)).perform(click());
//        intended(hasComponent(AddEntertainmentActivity.class.getName()));
//        Intents.release();
//        onView(withId(R.id.entertainmentTitleEditText)).perform(closeSoftKeyboard());
//        onView(isRoot()).perform(ViewActions.pressBack());
//        logout();
    }


    public void login() throws InterruptedException {
        main.getActivity().onLogout();
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
        Thread.sleep(1000);
    }
   // @Test
    public void search() throws InterruptedException {
       // login();
        //openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withContentDescription("Search")).perform(click());
        onView(withHint("Search for a spot")).perform(typeText("Bikini"));
        onView(withHint("Search for a spot"))
                .perform(pressImeActionButton());
        //onView(withText("Search for a spot")).perform(ViewActions.typeText("Bikini"));

    }
    //@Test
    public void Settings(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Settings")).perform(click());
        onView(withId(R.id.seekbar)).perform(ViewActions.swipeLeft());

    }

    public void logout(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Logout")).perform(click());
    }
}
