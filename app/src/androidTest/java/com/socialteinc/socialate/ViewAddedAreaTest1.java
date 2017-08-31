package com.socialteinc.socialate;

import android.content.Context;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)

public class ViewAddedAreaTest1 {

    @Rule
    public ActivityTestRule<ViewAddedActivity> mActivityRule =
            new ActivityTestRule<ViewAddedActivity>(ViewAddedActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent intent = new Intent(targetContext, MainActivity.class);
                    intent.putExtra("description_val", "Smoke House");
                    intent.putExtra("address_val", "23 Furry-Sniffle Rd., Socialate, 2017");
                    intent.putExtra("title_val", "Smoke House");
                    intent.putExtra("owner_val", "EGC Group");
                    Resources resources = InstrumentationRegistry.getTargetContext().getResources();
                    intent.putExtra("imageUri", (Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.mipmap.ic_launcher) + '/' +
                            resources.getResourceTypeName(R.mipmap.ic_launcher) + '/' +
                            resources.getResourceEntryName(R.mipmap.ic_launcher))).toString());
                    return intent;
                }
            };

    @Test
    public void ViewAreaOwenerTest(){
       //onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(not(isDisplayed())));

    }


}