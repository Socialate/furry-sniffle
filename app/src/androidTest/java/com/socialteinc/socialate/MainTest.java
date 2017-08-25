package com.socialteinc.socialate;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddEntertainment(){

//        Intents.init();
//        onView(withId(R.id.action_add_entertainment)).perform(click());
//        intended(hasComponent(AddEntertainmentActivity.class.getName()));
//        Intents.release();

    }

    @Test
    public void testLogout(){
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//        onView(withText("Logout")).perform(click());
//        intended(hasComponent(LoginActivity.class.getName()));
//        Intents.release();
    }
}
