package com.bombardier_gabriel.wizzenger.fragments.inputFragments;

/**
 * Created by gabb_ on 2018-03-14.
 * Inspired by: http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
 */

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.activities.RegisterActivity;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsernameInputFragment extends DialogFragment {
    public static String username;
    private EditText newUsernameZone;
    private Button validateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_alert_username, container, false);

        validateButton = (Button) rootView.findViewById(R.id.alert_username_validate);
        newUsernameZone = (EditText) rootView.findViewById(R.id.alert_username_entry);

        //Action lorsque l'on clique sur valider
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vérifier s'il existe deja
                username = newUsernameZone.getText().toString();
                FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> users = new ArrayList<User>();
                        String idUser = "";
                        for (com.google.firebase.database.DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                            User user = userDataSnapshot.getValue(User.class);
                            users.add(user);
                        }

                        for (User user : users){
                            if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                idUser = user.getid();
                                FirebaseDatabase.getInstance().getReference("users").child(idUser).child("username").setValue(username);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});
                Toast.makeText(getActivity(), "Le username a bien été modifié!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return rootView;
    }

}
