package com.socialteinc.socialate;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;


import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SearchableTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

     @Test
     public void searchTest() throws InterruptedException {
         onView((withId(R.id.search_btn))).check(matches(isDisplayed()));
         onView(allOf(withId(R.id.search_btn), withEffectiveVisibility(VISIBLE))).perform(click());
         onView((withHint("Search for a spot"))).perform(ViewActions.typeText("Bikini"), pressImeActionButton());
         Thread.sleep(2500);
         onView(withId(R.id.search_recyclerView))
                 .perform(RecyclerViewActions.scrollToPosition(0)).perform(click());
     }
}
