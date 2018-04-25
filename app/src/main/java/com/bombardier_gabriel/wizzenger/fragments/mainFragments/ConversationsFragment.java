package com.bombardier_gabriel.wizzenger.fragments.mainFragments;

import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.adapters.ConversationsAdapter;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 */
public class ConversationsFragment extends ListFragment {
    private RecyclerView listeConvo;
    private LinearLayoutManager mLayoutManager;
    private ConversationsAdapter mAdapter;
    private Vector<Conversation> convoList = new Vector<Conversation>();

    private DatabaseProfile myDatabase;

    public ConversationsFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        listeConvo = (RecyclerView) rootView.findViewById(R.id.recycler_conversations);
        mAdapter = new ConversationsAdapter(convoList, getActivity());
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        listeConvo.setLayoutManager(mLayoutManager);
        listeConvo.setItemAnimator(new DefaultItemAnimator());

        listeConvo.setAdapter(mAdapter);

        myDatabase = DatabaseProfile.getInstance();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getConvosInformations(FirebaseAuth.getInstance().getCurrentUser());
    }

    @Override
    public void onResume() {
        super.onResume();
//        convoList.clear();
//        getConvosInformations(FirebaseAuth.getInstance().getCurrentUser());
//
//        Comparator comparator = Collections.reverseOrder();
//        Collections.sort(convoList, comparator);

    }

    //Pour avoir les informations des conversations
    public void getConvosInformations(final FirebaseUser currentUser){
        FirebaseDatabase.getInstance().getReference("conversations").orderByChild("lastMessageDate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Conversation> convos = new ArrayList<Conversation>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Conversation convo = userDataSnapshot.getValue(Conversation.class);
                    convos.add(convo);
                }

                for (Conversation convo : convos){
                    if(convo.getIdUser1()!=null){
                       if(convo.getIdUser1().equals(currentUser.getEmail()) || convo.getIdUser2().equals(currentUser.getEmail())){
                           //La convo t'appartient, ajouter a la liste
                           if(convo.getIdUser1().equals(currentUser.getEmail())){
                               getUsername(convo.getIdUser2(), convo.getTexthint(), convo.getLastMessageDate());
                           }else if(convo.getIdUser2().equals(currentUser.getEmail())){
                               getUsername(convo.getIdUser1(), convo.getTexthint(), convo.getLastMessageDate());
                           }
                       }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour avoir le username d'un utilisateur en fonction de son email et l'ajoute a la liste
    public void getUsername(final String contactEmail, final String textHint, final String lastMessageDate){

        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (User user : users){
                    if(user.getEmail().equals(contactEmail)){
                        final Conversation temp = new Conversation();
                        temp.setContactName(user.getUsername());

                        //Détermine si le text hint est trop long pour l'écran
                        if(textHint.length()>25){
                            String newTextHint = textHint.substring(0,25) + "...";
                            temp.setTexthint(newTextHint);
                        }else{
                            temp.setTexthint(textHint);
                        }

                        //Détermine si le message a été envoyé aujourdhui
                        Date currentTime = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(currentTime);
                        int date = cal.get(Calendar.DAY_OF_MONTH);
                        Log.d("val", lastMessageDate.substring(8,10));
                        if(Integer.parseInt(lastMessageDate.substring(8,10)) == date){
                            temp.setLastMessageDate(lastMessageDate.substring(11,19));
                        }else{
                            temp.setLastMessageDate(lastMessageDate.substring(5,10));
                        }


                        // Pour aller chercher l'avatar
                        if(user.getAvatar()==1){
                            StorageReference pathReference = FirebaseStorage.getInstance().getReference().child("avatars/"+user.getEmail());
                            final long ONE_MEGABYTE = 1024 * 1024;
                            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    temp.setAvatar(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                                    convoList.add(temp);
                                    //mAdapter.notifyDataSetChanged();
                                    mAdapter.notifyItemRangeInserted(convoList.size() - 1,convoList.size());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    convoList.add(temp);
                                   //mAdapter.notifyDataSetChanged();
                                    mAdapter.notifyItemRangeInserted(convoList.size() - 1,convoList.size());
                                }
                            });
                        }else{
                            convoList.add(temp);
                            //mAdapter.notifyDataSetChanged();
                            mAdapter.notifyItemRangeInserted(convoList.size() - 1,convoList.size());
                        }
                        break;
                    }
                }

                //Pour scroller en haut,  a corriger
                //listeConvo.getLayoutManager().scrollToPosition(convoList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});


    }

}
