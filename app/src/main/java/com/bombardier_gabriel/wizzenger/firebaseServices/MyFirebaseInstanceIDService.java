package com.bombardier_gabriel.wizzenger.firebaseServices;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by gabb_ on 2018-01-13.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public static String currentToken;

    /**
     * Called if InstanceID token is updated.
     */

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        currentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + currentToken);
        sendRegistrationToServer(currentToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }



}
