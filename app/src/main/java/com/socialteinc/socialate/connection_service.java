package com.socialteinc.socialate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import static com.socialteinc.socialate.RegisterActivity.connect_receiver.PROCESS_RESPONSE;

/**
 * This class is constantly checking for connection validity
 * **/

public class connection_service extends IntentService {

    public connection_service() {
        super("connection_service");
    }
    //public final String PROCESS_RESPONSE = "com.socialteinc.socialate.intent.action.PROCESS_RESPONSE";
    public boolean default_result = true;
    public boolean result;
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intentResponse = new Intent();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        while(true) {
            result = cm.getActiveNetworkInfo() != null;
            intentResponse.setAction(PROCESS_RESPONSE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra("response", result);
            sendBroadcast(intentResponse);
        }
    }

}
