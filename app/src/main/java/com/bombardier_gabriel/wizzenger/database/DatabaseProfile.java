package com.bombardier_gabriel.wizzenger.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.bombardier_gabriel.wizzenger.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabb_ on 2018-01-08.
 * Inspired by Google Firebase examples
 */

public class DatabaseProfile {
    private static final String TAG = "DatabaseProfile";
    private static DatabaseProfile instance;
    private DatabaseReference usersDatabase, contactsDatabase;
    private User user;
    private Context context;

    private DatabaseProfile(final Context context) {
        this.context = context;
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");
        contactsDatabase = FirebaseDatabase.getInstance().getReference("contacts");
    }

    public static void init(final Context context) {
        instance = new DatabaseProfile(context);
    }

    public static DatabaseProfile getInstance() {
        if(instance == null) {
            throw new IllegalStateException("You must init the database in the application class.");
        }

        return instance;
    }

    public void initMyUser() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            addProfileEventListener();
        }
    }

    private void addProfileEventListener() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i(TAG, "addProfileEventListener: uid=" + uid);
        usersDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                addFCMToken(context);
                Log.d(TAG, "User name: " + user.getUserName() + ", email " + user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //Pour écrire un usager dans la base de données
    public void writeUser(User user){
        this.user = user;
        //Créer une image d'avatar
        //Bitmap bitmap = AvatarGenerator.generate(context, 50, 50);
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        //user.thumbnailBase64 = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);

        String key =  usersDatabase.push().getKey();
        usersDatabase.child(key).child("id").setValue(key);
        usersDatabase.child(key).child("username").setValue(user.getUserName());
        usersDatabase.child(key).child("email").setValue(user.getEmail());
        usersDatabase.child(key).child("phone").setValue(user.getPhone());
//        usersDatabase.child(user.getUserName()).child("password").setValue(user.getPassword());
        usersDatabase.child(key).child("avatar").setValue(user.getPhotoUrl());
//        addProfileEventListener();
    }

    //Pour écrire un contact dans la base de données
    public void writeContact(final FirebaseUser currentUser, final String contactEmail){
        final String key =  contactsDatabase.push().getKey();
        contactsDatabase.child(key).child("id").setValue(key);

        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (User user : users){
                    if(user.getEmail().equals(currentUser.getEmail())){
                        contactsDatabase.child(key).child("userID").setValue(user.getid());
//                        contactsDatabase.child(key).child("username").setValue(user.getUserName());
                    }
                    if(user.getEmail().equals(contactEmail)){
                        contactsDatabase.child(key).child("contactID").setValue(user.getid());
//                        contactsDatabase.child(key).child("username").setValue(user.getUserName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    public void addFCMToken(final Context context) {
        FirebaseApp.getInstance().getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    final String token = task.getResult().getToken();
                    String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    Log.i(TAG, "onFCMFetchSuccessful");
                    if (user.hasFcmTokenChanged(deviceId, token)) {
                        Log.i(TAG, "onFCMFetchSuccessful: user.devices=" + user.getDevicesList().size());
                        usersDatabase.child(user.getUserName()).child("devices").setValue(user.getDevicesList());
                    }
                }
            }
        });
    }
}
