package com.bombardier_gabriel.wizzenger.database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.adapters.ContactsAdapter;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.Message;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by gabb_ on 2018-01-08.
 * Inspired by Google Firebase examples
 */

public class DatabaseProfile {
    private static final String TAG = "DatabaseProfile";
    private static DatabaseProfile instance;
    private DatabaseReference usersDatabase, contactsDatabase, convosDatabase;
    private User user;
    private Context context;
    DatabaseReference myRef;

    private DatabaseProfile(final Context context) {
        this.context = context;
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        usersDatabase = rootRef.child("users");
        contactsDatabase = FirebaseDatabase.getInstance().getReference("contacts");
        convosDatabase = FirebaseDatabase.getInstance().getReference("conversations");
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

    //Permet de recharger les informations d'un usager s'il les change
    private void addProfileEventListener() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i(TAG, "addProfileEventListener: uid=" + uid);
        usersDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                addFCMToken(context);
                Log.d(TAG, "User name: " + user.getUsername() + ", email " + user.getEmail());
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

        String key =  usersDatabase.push().getKey();
        usersDatabase.child(key).child("id").setValue(key);
        usersDatabase.child(key).child("username").setValue(user.getUsername());
        usersDatabase.child(key).child("email").setValue(user.getEmail());
        usersDatabase.child(key).child("phone").setValue(user.getPhone());
        usersDatabase.child(key).child("avatar").setValue(user.getPhotoUrl());
//        addProfileEventListener();
    }

    //Pour créer un contact dans la base de données
    public void writeContact(final FirebaseUser currentUser, final String contactEmail, final Activity currentActivity){
        final String key =  contactsDatabase.push().getKey();

        usersDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                boolean trouveUser = false;
                boolean trouveNouveau = false;
                String emailUser="", emailNouveau="";

                for (com.google.firebase.database.DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (User user : users){
                    if(user.getEmail().equals(currentUser.getEmail())){
                        emailUser = user.getEmail();
                        trouveUser = true;
                    }
                    if(user.getEmail().equals(contactEmail)){
                        emailNouveau = user.getEmail();
                        trouveNouveau = true;
                    }
                }

                if(trouveUser == false || trouveNouveau == false){
                    Toast.makeText(currentActivity, "Erreur, l'usager n'existe pas", Toast.LENGTH_LONG).show();
                }else{
                    contactsDatabase.child(key).child("id").setValue(key);
                    contactsDatabase.child(key).child("userID").setValue(emailUser);
                    contactsDatabase.child(key).child("contactID").setValue(emailNouveau);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour supprimer un contact dans la base de données (seulement le lien, pas le user)
    public void removeContact(final FirebaseUser currentUser, final String contactEmail, final Activity currentActivity){
//        final String key =  contactsDatabase.push().getKey();
//        contactsDatabase.child(key).child("id").setValue(key);
//
//        usersDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<User> users = new ArrayList<User>();
//                boolean trouve = false;
//                for (com.google.firebase.database.DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
//                    User user = userDataSnapshot.getValue(User.class);
//                    users.add(user);
//                }
//
//                for (User user : users){
//                    if(user.getEmail().equals(currentUser.getEmail())){
//                        contactsDatabase.child(key).child("userID").setValue(user.getEmail());
//                        trouve = true;
//                    }
//                    if(user.getEmail().equals(contactEmail)){
//                        contactsDatabase.child(key).child("contactID").setValue(user.getEmail());
//                        trouve = true;
//                    }
//                }
//                if(trouve ==false){
//                    Toast.makeText(currentActivity, "Erreur, l'usager n'existe pas", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour ouvrir une conversation dans la base de données
    public void writeConvoUsers(final FirebaseUser currentUser, final String contactEmail){
        final String key =  contactsDatabase.push().getKey();
        convosDatabase.child(key).child("id").setValue(key);

        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (User user : users){
                    if(user.getEmail().equals(currentUser.getEmail())){
                        convosDatabase.child(key).child("idUser1").setValue(user.getEmail());
                    }
                    if(user.getEmail().equals(contactEmail)){
                        convosDatabase.child(key).child("idUser2").setValue(contactEmail);
//                        contactsDatabase.child(key).child("username").setValue(user.getUserName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
    }

    public void updateTextHint(String id, String message){
        Map<String, Object> valUpdate = new HashMap<String, Object>();
        valUpdate.put("textHint", message);
        convosDatabase.child(id).updateChildren(valUpdate);
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
                        usersDatabase.child(user.getUsername()).child("devices").setValue(user.getDevicesList());
                    }
                }
            }
        });
    }
}
