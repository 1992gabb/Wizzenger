package com.bombardier_gabriel.wizzenger;

import android.app.Application;

import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gabb_ on 2018-03-22.
 */

public class WizzApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseProfile.init(this.getApplicationContext());
    }
}
