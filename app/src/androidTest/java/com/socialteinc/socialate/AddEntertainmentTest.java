//package com.socialteinc.socialate;
//
//
//import android.support.test.filters.LargeTest;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.view.WindowManager;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.*;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//
//public class AddEntertainmentTest {
//    private String nameOfPlace;
//    private String nameOfOwner;
//    private String physicalAddress;
//    private String description;
//    @Rule
//    public ActivityTestRule<AddEntertainmentActivity> rule = new ActivityTestRule<>(AddEntertainmentActivity.class);
//    @Before
//    public void initValidString() {
//        // Specify a valid string.
//        nameOfPlace = "Stones";
//        //tyuujyjyjy
//        nameOfOwner = "Siphe";
//        physicalAddress = "Men's Hall of Residence";
//        description = "Kuyafiwa la baba!!!";
//
//    }
//
//    @Before
//    public void unlockScreen() {
//        final AddEntertainmentActivity activity = rule.getActivity();
//        Runnable wakeUpDevice = new Runnable() {
//            public void run() {
//                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
//                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
//                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            }
//        };
//        activity.runOnUiThread(wakeUpDevice);
//    }
//
//    @Test
//    public void test1() throws InterruptedException{
//        Thread.sleep(3000);
//        onView(withId(R.id.entertainmentTitleEditText)).check(matches(isClickable()));
//        onView(withId(R.id.entertainmentTitleEditText)).perform(scrollTo(), typeText(nameOfPlace));
//        onView(withId(R.id.entertainmentTitleEditText)).check(matches(withText(nameOfPlace)));
//
//    }
//
//    @Test
//    public void test2() throws InterruptedException{
//        Thread.sleep(3000);
//        onView(withId(R.id.chooseImageButton)).check(matches(isClickable()));
//       // onView(withId(R.id.chooseImageButton)).perform(click());
//    }
//
//    /*@Test
//    public void test3() throws InterruptedException{
//        Thread.sleep(3000);
//        onView(withId(R.id.scrollView)).perform(swipeUp());
//    }*/
////
//    @Test
//    public void test4() throws InterruptedException{
//        Thread.sleep(3000);
//        //onView(withId(R.id.scrollView)).perform(swipeUp());
//        onView(withId(R.id.NameOfOwnerEditText)).check(matches(isClickable()));
//        onView(withId(R.id.NameOfOwnerEditText)).perform(scrollTo(), typeText(nameOfOwner));
//        onView(withId(R.id.NameOfOwnerEditText)).check(matches(withText(nameOfOwner)));
//    }
//
//    @Test
//    public void test5() throws InterruptedException{
//        Thread.sleep(3000);
//        onView(withId(R.id.entertainmentAddressEditText)).check(matches(isClickable()));
//        onView(withId(R.id.entertainmentAddressEditText)).perform(scrollTo(), typeText(physicalAddress));
//        onView(withId(R.id.entertainmentAddressEditText)).check(matches(withText(physicalAddress)));
//    }
//
//    @Test
//    public void test6() throws InterruptedException{
//        Thread.sleep(3000);
//        onView(withId(R.id.entertainmentDescrptionEditText)).check(matches(isClickable()));
//        onView(withId(R.id.entertainmentDescrptionEditText)).perform(scrollTo(), typeText(description));
//        onView(withId(R.id.entertainmentDescrptionEditText)).check(matches(withText(description)));
//    }
//
//    @Test
//    public void test7() throws InterruptedException{
//        //Thread.sleep(3000);
//        onView(withId(R.id.addEntertainmentAreaButton)).check(matches(isClickable()));
//        onView(withId(R.id.addEntertainmentAreaButton)).perform(scrollTo(), click());
//    }
////
//
//
//
//}
