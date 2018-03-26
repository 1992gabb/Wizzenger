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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Collections;
import java.util.Comparator;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        convoList.clear();
        getConvosInformations(FirebaseAuth.getInstance().getCurrentUser());

        Comparator comparator = Collections.reverseOrder();

        Collections.sort(convoList, comparator);

    }

    //Pour tester des fonctionnalités sans BD
    private void getConversations() {

        Conversation convo = new Conversation(R.drawable.mario, "babriel", "regarde mon dunk");
        convoList.add(convo);

        Conversation convo2 = new Conversation(R.drawable.mario, "login_rounded_border", "regarde moi");
        convoList.add(convo2);

        Conversation convo3 = new Conversation(R.drawable.mario, "manolo", "regarde moi");
        convoList.add(convo3);

        mAdapter.notifyDataSetChanged();
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
                               getUsername(convo.getIdUser2(), convo.getTexthint());
                           }else if(convo.getIdUser2().equals(currentUser.getEmail())){
                               getUsername(convo.getIdUser1(), convo.getTexthint());
                           }
                       }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour avoir le username d'un utilisateur en fonction de son email et l'ajoute a la liste
    public void getUsername(final String contactEmail, final String textHint){

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
                        // Pour aller chercher l'avatar
                        StorageReference pathReference = FirebaseStorage.getInstance().getReference().child("avatars/"+user.getEmail());
                        final Conversation temp = new Conversation();
                        temp.setContactName(user.getUsername());
                        temp.setTexthint(textHint);

                        final long ONE_MEGABYTE = 1024 * 1024;
                        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                temp.setAvatar(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                                convoList.add(temp);
                                mAdapter.notifyItemInserted(convoList.size() - 1);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                convoList.add(temp);
                                mAdapter.notifyItemInserted(convoList.size() - 1);
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});


    }

}
