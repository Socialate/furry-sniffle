package com.socialteinc.socialate;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)

public class SearchableTest2 {

    @Rule
    public ActivityTestRule<SearchableActivity> rule = new ActivityTestRule<>(SearchableActivity.class);

    @Test
    public void doMySearchTest(){
        assertEquals(rule.getActivity().doMySearch("Bikini"), true);
    }

}
