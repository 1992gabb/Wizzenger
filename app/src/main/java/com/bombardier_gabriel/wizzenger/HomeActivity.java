package com.bombardier_gabriel.wizzenger;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.fragments.FragmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager viewPager;
    private TextView textZone;
    private String currentToken;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*//If no user connected
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            LoginActivity.show(this);
            finish();
        }*/



        FirebaseUser user = firebaseAuth.getCurrentUser();
        currentToken = FirebaseInstanceId.getInstance().getToken();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        textZone = (TextView) findViewById(R.id.text_field);

        textZone.setText(user.getEmail());

        if(getIntent().getExtras()!=null){
            for(String key: getIntent().getExtras().keySet()){
                if(key.equals("title")){
                    textZone.setText(key);
                }
            }
        }

        FragmentAdapter fragAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(fragAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        /*if(v.getId()==R.id.settings_btn){
            //ToDo show profile
            FirebaseAuth.getInstance().signOut();
            LoginActivity.show(this);
            finish();
        }*/
    }
}
