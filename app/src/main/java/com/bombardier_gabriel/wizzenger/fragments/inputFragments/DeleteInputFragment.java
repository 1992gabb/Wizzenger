package com.bombardier_gabriel.wizzenger.fragments.inputFragments;

/**
 * Created by gabb_ on 2018-03-14.
 * Inspired by: http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.google.firebase.auth.FirebaseAuth;

public class DeleteInputFragment extends DialogFragment {
    public static String email = "";
    private TextView title;
    private Button validateButton, noButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_alert_delete, container, false);

        final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        //vibrator.vibrate(100);

        validateButton = (Button) rootView.findViewById(R.id.alert_button_yes);
        noButton = (Button) rootView.findViewById(R.id.alert_button_no);
        title = (TextView) rootView.findViewById(R.id.alert_delete_title);

        Bundle args = getArguments();

        if(args!=null){
            email = args.getString("contactEmail");
        }

        //Action lorsque l'on clique sur valider
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseProfile.getInstance().removeContact(FirebaseAuth.getInstance().getCurrentUser(), email, getActivity());
                dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    public void createEmailInputFragment(){

    }
}
