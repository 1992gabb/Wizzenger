package com.bombardier_gabriel.wizzenger.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.model.Conversation;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Classe inspirée de https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.MyViewHolder> {

    private List<Conversation> convoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView nomContact, texteApercu;

        public MyViewHolder(View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.imgContact);
            nomContact=(TextView) view.findViewById(R.id.nomContactConvo);
            texteApercu=(TextView) view.findViewById(R.id.texteApercu);
        }
    }


    public ConversationsAdapter(List<Conversation> convoList) {
        this.convoList = convoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_one_conversation, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Conversation convo = convoList.get(position);
        holder.image.setImageResource(convo.getImage());
        holder.nomContact.setText(convo.getNomContact());
        holder.texteApercu.setText(convo.getTexteApercu());
    }

    @Override
    public int getItemCount() {
        return convoList.size();
    }

}
