package com.bombardier_gabriel.wizzenger.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.adapters.FragmentAdapter;
import com.bombardier_gabriel.wizzenger.firebaseServices.MyFirebaseInstanceIDService;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager viewPager;
    private TextView textZone;
    private ImageView boutonParam;
    private String currentToken;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseProfile myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_home);

        myDatabase = DatabaseProfile.getInstance();

        //If no user connected
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            LoginActivity.show(this);
            finish();
        }else{
            currentToken = FirebaseInstanceId.getInstance().getToken();
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        currentToken = FirebaseInstanceId.getInstance().getToken();
        addTokenToUser(currentToken);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        boutonParam = (ImageView) findViewById(R.id.settings_btn);

        FragmentAdapter fragAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(fragAdapter);
        viewPager.setOffscreenPageLimit(2);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        boutonParam.setOnClickListener(this);
    }

    private void addTokenToUser(final String currentToken) {
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (final User user : users) {
                    if (user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        FirebaseDatabase.getInstance().getReference("users").child(user.getid()).child("token").setValue(currentToken);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.settings_btn){
            Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(i);
        }
    }
}
