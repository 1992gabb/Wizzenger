package com.bombardier_gabriel.wizzenger.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.ConversationActivity;
import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.fragments.ConversationsFragment;
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
        public TextView contactName, textHint;

        public MyViewHolder(View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.imgContactConvo);
            contactName=(TextView) view.findViewById(R.id.nomContactConvo);
            textHint=(TextView) view.findViewById(R.id.texteApercu);
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

    //Solution pour le listener trouvée ici: https://stackoverflow.com/questions/42721571/recyclerview-onitemclicklistener
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Conversation convo = convoList.get(position);
        holder.image.setImageResource(convo.getConvoImage());
        holder.contactName.setText(convo.getContactName());
        holder.textHint.setText(convo.getTexthint());

//        if(convo.getTexthint().equals("**Un bon vieux Wizz**")){
//            holder.image.startAnimation(AnimationUtils.loadAnimation(holder.contactName.getContext(), R.anim.wizz_animation));
//            holder.contactName.startAnimation(AnimationUtils.loadAnimation(holder.contactName.getContext(), R.anim.wizz_animation));
//            holder.textHint.startAnimation(AnimationUtils.loadAnimation(holder.contactName.getContext(), R.anim.wizz_animation));
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ConversationActivity.class);
                i.putExtra("contactName", holder.contactName.getText());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return convoList.size();
    }

}
