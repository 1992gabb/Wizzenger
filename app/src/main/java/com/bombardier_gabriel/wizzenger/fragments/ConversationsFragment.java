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
import com.bombardier_gabriel.wizzenger.model.Conversation;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationsFragment extends ListFragment {
    private SimpleAdapter adapter;
    private Vector<Hashtable<String, String>> vecDonnees;
    private Hashtable<String, String> hConvoTemp;
    private ListView listeConversations;

    private RecyclerView listeConvo;
    private ConversationsAdapter mAdapter;
    private Vector<Conversation> convoList = new Vector<Conversation>();

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

        getConversations();

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

    private void getConversations() {
        //Aller chercher les convo en lien avec l'usager

        Conversation convo = new Conversation(R.drawable.mario, "Mario Bros", "regarde mon dunk");
        convoList.add(convo);

        Conversation convo2 = new Conversation(R.drawable.mario, "Le moi", "regarde moi");
        convoList.add(convo2);

        mAdapter.notifyDataSetChanged();
    }

}
