//package com.socialteinc.socialate;
//
//import android.content.Intent;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.pressBack;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class googleTest {
//
//    @Rule
//    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);
//
//    //ActivityTestRule<>(RegisterActivity.class, true, false) //rule for activity start
//    // IntentTestRule<>(ActivityYouWantToStart.class, initialTouchMode, launchActivity) //used for testing intents and activities
//
//    @Before
//    public void init() {
//        rule.getActivity();
//        onView(withId(R.id.creatAccountTextView)).perform(click());
//    }
//
//    @Test
//    public void isDisplayedTest() throws InterruptedException {
//        Thread.sleep(2000);
//        onView(withId(R.id.googleButton)).check(matches(isDisplayed()));
//        Thread.sleep(2000);
//
//    }
//
//    @Test
//    public void test1() throws InterruptedException {
//        onView(withId(R.id.googleButton)).perform(click());
//        Thread.sleep(3000);
//    }
//
//    @After
//    public void stopActivity(){
//        rule.finishActivity();
//    }
//
//}