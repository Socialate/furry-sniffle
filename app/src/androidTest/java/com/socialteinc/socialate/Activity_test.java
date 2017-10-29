package com.socialteinc.socialate;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
    public class Activity_test {

        @Rule
        public ActivityTestRule<Activity_nearby> mActivityRule = new ActivityTestRule<>(
                Activity_nearby.class);

        /**
         * This is a test for Checking if the username has already being taken or not,
         * if username doesn't exist, you'll be allowed to create a password
         **/

        @Test
        public void sort_bar_test(){
//
//            assertEquals(50,Activity_nearby.translateSeekbar(2));
//            ArrayList<Float> location = new ArrayList<>();
//            location.add((float) 15.2);
//            location.add((float) 78.54);
//            ArrayList<String> keys = new ArrayList<>();
//            keys.add("-rode");
//            keys.add("-dive");
//            String[] arr = {"-rode","-dive"};
//            assertNotNull( Activity_nearby.insertionSort(keys,location));

        }

        @Test
        public void test_ui(){
            onView(withId(R.id.nearby_recyclerView)).perform(ViewActions.click());
    }




    }


