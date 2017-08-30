package com.socialteinc.socialate;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.not;

public class ViewAddedAreaTest2 {
    @Rule
    public ActivityTestRule<ViewAddedActivity> mActivityRule1 =
            new ActivityTestRule<ViewAddedActivity>(ViewAddedActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent intent = new Intent(targetContext, MainActivity.class);
                    intent.putExtra("description_val", "Smoke House");
                    intent.putExtra("address_val", "23 Furry-Sniffle Rd., Socialate, 2017");
                    intent.putExtra("title_val", "Smoke House");
                    intent.putExtra("owner_val", "");
                    Resources resources = InstrumentationRegistry.getTargetContext().getResources();
                    intent.putExtra("imageUri", (Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.mipmap.ic_launcher) + '/' +
                            resources.getResourceTypeName(R.mipmap.ic_launcher) + '/' +
                            resources.getResourceEntryName(R.mipmap.ic_launcher))).toString());
                    return intent;
                }
            };

    @Test
    public void ViewAreaTestText() {
        onView(withId(R.id.ViewAddedAreaOwner)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(not(isDisplayed())));
    }

    @Test
    public void ViewAreaBackButtonTest(){
        onView(withText("Entertainment Area")).perform(longClick());
        onView(withContentDescription(R.string.abc_action_bar_up_description)).check(matches(isDisplayed()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

    }
}
