package com.bombardier_gabriel.wizzenger.fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.adapters.ContactsAdapter;
import com.bombardier_gabriel.wizzenger.adapters.ConversationsAdapter;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.User;

import java.util.Hashtable;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private SimpleAdapter adapter;
    private Vector<Hashtable<String, String>> vecDonnees;
    private Hashtable<String, String> hConvoTemp;
    private ListView listeConversations;

    private RecyclerView listeContacts;
    private ContactsAdapter mAdapter;
    private Vector<User> contactsList = new Vector<User>();

    public ContactsFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        listeContacts = (RecyclerView) rootView.findViewById(R.id.recycler_conversations);
        mAdapter = new ContactsAdapter(contactsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listeContacts.setLayoutManager(mLayoutManager);
        listeContacts.setItemAnimator(new DefaultItemAnimator());
        listeContacts.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        listeContacts.setAdapter(mAdapter);

        getContacts();

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*hhConvoTemp = new Hashtable<String, String>();
        vecDonnees = new Vector<Hashtable<String, String>>();
        listeConversations = (ListView) rootView.findViewById(android.R.id.list);


        ConvoTemp.put("imgContact", String.valueOf(R.drawable.mario));
        hConvoTemp.put("nomContactConvo", "Mario Bros");
        hConvoTemp.put("texteApercu", "As-tu vu mon étoile?");
        String from[]={"imgContact","nomContactConvo", "texteApercu"};
        int to[] = {R.id.imgContact, R.id.nomContactConvo, R.id.texteApercu};

        vecDonnees.add(hConvoTemp);

        adapter = new SimpleAdapter(getContext(), vecDonnees,R.layout.layout_one_conversation, from, to);
        listeConversations.setAdapter(adapter);*/

    }

    private void getContacts() {
        //Aller chercher les conntacts en lien avec l'usager

        User user = new User(R.drawable.mario, "Mario Bros");
        contactsList.add(user);

        User user2 = new User(R.drawable.mario, "Luigi");
        contactsList.add(user2);

        mAdapter.notifyDataSetChanged();
    }

}
