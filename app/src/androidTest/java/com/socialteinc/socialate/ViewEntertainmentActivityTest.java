package com.socialteinc.socialate;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewEntertainmentActivityTest {

    @Rule
    public ActivityTestRule<ViewEntertainmentActivity> rule = new ActivityTestRule<ViewEntertainmentActivity>(ViewEntertainmentActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, ViewEntertainmentActivity.class);
            String entertainmentKey = "-KsoZRktbnUQhBp8_dV-";
            String entertainmentName = "Tiger";
            result.putExtra("entertainmentName", entertainmentName);
            result.putExtra("entertainmentKey", entertainmentKey);
            return result;
        }
    };

    @Test
    public void onCreateTest() throws InterruptedException {
        Thread.sleep(5000);
    }

}