//package com.socialteinc.socialate;
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.database.FirebaseDatabase;
//import com.jakewharton.picasso.OkHttp3Downloader;
//import com.squareup.picasso.Picasso;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(AndroidJUnit4.class)
//public class SocialateTest {
//
//    @Rule
//    public ActivityTestRule<ResetPasswordActivity> rule = new ActivityTestRule<>(ResetPasswordActivity.class);
//
//    @Test
//    public void test1() throws InterruptedException{
//        Socialate obj = new Socialate();
//        obj.onCreate();
//
//        if(!FirebaseApp.getApps(obj.getApplicationContext()).isEmpty()){
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }
//
//        Picasso.Builder builder = new Picasso.Builder(obj.getApplicationContext());
//        builder.downloader(new OkHttp3Downloader(obj.getApplicationContext(), Integer.MAX_VALUE));
//        Picasso built = builder.build();
//        built.setIndicatorsEnabled(false);
//        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);
//    }
//}
