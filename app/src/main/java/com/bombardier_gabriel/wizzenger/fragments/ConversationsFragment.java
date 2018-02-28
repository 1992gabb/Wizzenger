package com.bombardier_gabriel.wizzenger.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bombardier_gabriel.wizzenger.HomeActivity;
import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.adapters.ConversationsAdapter;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 *
 */
public class ConversationsFragment extends ListFragment {
    private RecyclerView listeConvo;
    private ConversationsAdapter mAdapter;
    private Vector<Conversation> convoList = new Vector<Conversation>();

    private DatabaseProfile myDatabase;

    public ConversationsFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        listeConvo = (RecyclerView) rootView.findViewById(R.id.recycler_conversations);
        mAdapter = new ConversationsAdapter(convoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listeConvo.setLayoutManager(mLayoutManager);
        listeConvo.setItemAnimator(new DefaultItemAnimator());
        listeConvo.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        listeConvo.setAdapter(mAdapter);

        myDatabase = DatabaseProfile.getInstance();

        getConvosInformations(FirebaseAuth.getInstance().getCurrentUser());

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //Pour tester des fonctionnalités sans BD
    private void getConversations() {

        Conversation convo = new Conversation(R.drawable.mario, "babriel", "regarde mon dunk");
        convoList.add(convo);

        Conversation convo2 = new Conversation(R.drawable.mario, "aaa", "regarde moi");
        convoList.add(convo2);

        Conversation convo3 = new Conversation(R.drawable.mario, "manolo", "regarde moi");
        convoList.add(convo3);

        mAdapter.notifyDataSetChanged();
    }

    //Pour avoir les informations des conversations
    public void getConvosInformations(final FirebaseUser currentUser){
        FirebaseDatabase.getInstance().getReference("conversations").addListenerForSingleValueEvent(new ValueEventListener() {
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
                        convoList.add(new Conversation(R.drawable.mario, user.getUsername(), textHint));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
    }

}
