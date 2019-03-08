package com.bombardier_gabriel.wizzenger.fragments.inputFragments;

/**
 * Created by gabb_ on 2018-03-14.
 * Inspired by: http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvatarInputFragment extends DialogFragment {
    public static String email;
    public android.net.Uri avatarURI;
    private EditText emailZone;
    private Button chooseButton, validateButton;
    private StorageReference storageRef;
    private int requestCode = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            if (resultData != null) {

                //Pour réduire la taille de l'image
                avatarURI = resultData.getData();
                byte[] data = {};
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), avatarURI);

                    float aspectRatio = bmp.getWidth() /(float) bmp.getHeight();
                    int width = 256;
                    int height = Math.round(width / aspectRatio);

                    Bitmap newImage = Bitmap.createScaledBitmap(bmp, width, height, false);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    newImage.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                    data = baos.toByteArray();

                    bmp.recycle();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                StorageReference newAvatarRef = storageRef.child("avatars").child(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                UploadTask uploadTask = newAvatarRef.putBytes(data);

                FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> users = new ArrayList<User>();
                        for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                            User user = userDataSnapshot.getValue(User.class);
                            users.add(user);
                        }

                        for (User user : users){
                            if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                FirebaseDatabase.getInstance().getReference("users").child(user.getid()).child("avatar").setValue(1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }});

                if(uploadTask!=null){
                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity(), "Ca marche po", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Toast.makeText(getActivity(), "Ca marche!!!", Toast.LENGTH_SHORT).show();
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            dismiss();
                        }
                    });
                }

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Modifier avatar");
        final View rootView = inflater.inflate(R.layout.layout_alert_avatar, container, false);

        validateButton = (Button) rootView.findViewById(R.id.alert_avatar_validate);

        storageRef = FirebaseStorage.getInstance().getReference();

        //Action lorsque l'on clique sur valider
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a reference to "mountains.jpg"
                StorageReference avatarRef = storageRef.child("avatars");

                //Pour enregistrer dans la BD
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, requestCode);

                //Le code ici est appellé dans le onActivityResult
            }
        });

        return rootView;
    }
}
