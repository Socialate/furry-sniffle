package com.socialteinc.socialate;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;
/**
 * This class is constantly checking for connection validity
 * **/

public class connection_service extends IntentService {

    public connection_service() {
        super("connection_service");
    }

    public static final String RESPONSE_MESSAGE = "myResponseMessage";
    public boolean result;
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intentResponse = new Intent();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        while(true) {
            result = cm.getActiveNetworkInfo() != null;
            intentResponse.setAction(MainActivity.connect_receiver.PROCESS_RESPONSE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra("response", result);
            sendBroadcast(intentResponse);
        }
    }

}
