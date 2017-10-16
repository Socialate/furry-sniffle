//package com.socialteinc.socialate;
//
//import android.app.SearchManager;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.test.espresso.Espresso;
//import android.support.test.espresso.action.ViewActions;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.util.Log;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import junit.framework.Assert;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//import static android.content.Intent.getIntent;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
//import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.*;
//
//@RunWith(AndroidJUnit4.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class MainTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);
//    private  FirebaseAuth mAuth;
//
//
//@Before    public void         login(){
//FirebaseApp.initializeApp(main.getActivity());
//mAuth = FirebaseAuth.getInstance();
//
//mAuth.signInWithEmailAndPassword("joe@gmail.com", "sandile")
//.addOnCompleteListener(main.getActivity(), new OnCompleteListener<AuthResult>(){
//@Override
//public void onComplete(@NonNull Task<AuthResult> task){
//if (task.isSuccessful()) {
//    // Sign in success, update UI with the signed-in user's information
////                            Looper.prepare();
////                            ViewOtherUserProfile obj = new ViewOtherUserProfile();
////                            boolean chechTest = true;
////                            //Bundle b = new Bundle();
////                            //obj.b.putBoolean("chechTest", chechTest);
////                            //obj.startActivity(new Intent(obj.getApplicationContext(), ViewOtherUserProfile.class), putExtra("checkTest", checkTest));
////                            Intent intent = new Intent(ViewOtherUserProfile, MainActivity.class);
////                            intent.putExtra("cc", chechTest);
////                            obj.startActivity(intent);
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("login activity", "signInWithEmail:failure", task.getException());
//
//                        }
//                    }
//                });
//    }
//
//    @Test
//    public void isDisplayedSearchTest() throws InterruptedException {
//       Thread.sleep(8000);
//        onView(withId(R.id.search_btn)).check(matches(isDisplayed()));
////        Intent intent = getIntent();
////        Assert.assertEquals(Intent.ACTION_SEARCH, intent.getAction());
////        String query = intent.getStringExtra(SearchManager.QUERY);
//    }
//
//    @Test
//    public void searchTest() throws InterruptedException {
//        Thread.sleep(15000);
//       // onView(withId(R.id.search_btn)).perform(click());
//       // onView(withHint("Search for a spot")).check(matches(isDisplayed()));
//       // onView(withHint("Search for a spot"))
//                //.perform(typeText("Mike"));
//        onView(withHint("Search for a spot")).perform(pressImeActionButton());
//Thread.sleep(5000);
//    }
//
//    @Test
//    public void isDisplayedTest() throws InterruptedException{
//        Thread.sleep(9000);
//        onView(withId(R.id.mainPageToolBar)).check(matches(isDisplayed()));
//
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//        onView(withText("Edit Profile")).check(matches(isDisplayed()));
//        onView(withText("Settings")).check(matches(isDisplayed()));
//        onView(withText("Logout")).check(matches(isDisplayed()));
//        Thread.sleep(3000);
//
//    }
//
//    @Test
//    public void EditProfileTest() throws InterruptedException{
//        Thread.sleep(8000);
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//        onView(withText("Edit Profile")).perform(click());
//        Thread.sleep(8000);
//    }
//
//    @Test
//    public void settingsTest() throws InterruptedException{
//        Thread.sleep(9000);
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//        onView(withText("Settings")).perform(click());
//        onView(withId(R.id.seekbar)).check(matches(isDisplayed()));
//        onView(withId(R.id.seekbar)).perform(ViewActions.swipeLeft());
//        Thread.sleep(3000);
//
//    }
//
//    @Test
//    public void logoutTest() throws InterruptedException{
//        Thread.sleep(8000);
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//        onView(withText("Logout")).perform(click());
//        Thread.sleep(3000);
//    }
//}
