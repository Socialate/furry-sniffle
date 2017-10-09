//package com.socialteinc.socialate;
//
//import android.app.ProgressDialog;
//import android.os.Looper;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//
//public class CheckEmailTest2 {
//
//    @Rule
//    public ActivityTestRule<CheckEmailActivity> rule = new ActivityTestRule<>(CheckEmailActivity.class);
//
//    public final static String emailTest = "joe@gmail.com";
//    private ProgressDialog mProgressDialogTest;
//
////    @Test
////    public void test1() throws InterruptedException{
////        Looper.prepare();
////        CheckEmailActivity obj = new CheckEmailActivity();
////        mProgressDialogTest = new ProgressDialog(obj.getApplication()); // problem here
////
////        onView(withId(R.id.emailEditText)).perform(typeText(emailTest));
////        onView(withId(R.id.nextButton)).perform(click());
////
////        if(!CheckEmailActivity.isValidEmail(emailTest)){return;}
////
////        mProgressDialogTest.setTitle("Checking Email Address");
////        mProgressDialogTest.setMessage("Please wait while we check email.");
////        mProgressDialogTest.setCanceledOnTouchOutside(false);
////        mProgressDialogTest.show();
////
////        Looper.prepare();
////        obj = new CheckEmailActivity();
////        mProgressDialogTest.dismiss();
////        obj.checkAccountEmailExistsInFirebase(emailTest);
////        Thread.sleep(3000);
////
////    }
//
////    @Test
////    public void checkAccountEmailExistsInFirebaseTest() throws InterruptedException{
////
////        Looper.prepare();
////        CheckEmailActivity obj = new CheckEmailActivity();
////        mProgressDialogTest.dismiss();
////        obj.checkAccountEmailExistsInFirebase(emailTest);
////        Thread.sleep(3000);
////    }
//
////    @Test
////    public void test1(){
////        Looper.prepare();
////        CheckEmailActivity obj = new CheckEmailActivity();
////        //obj.mProgressDialog.dismiss();
////        obj.signUpUser(emailTest);
////    }
//
//}
