package com.bombardier_gabriel.wizzenger.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.activities.ConversationActivity;
import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Classe inspirée de https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.MyViewHolder> {

    private List<Conversation> convoList;
    public Activity activity;
    private RequestOptions requestOptions;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView contactName, textHint, timeStamp;

        public MyViewHolder(View view) {
            super(view);
            avatar= (ImageView) view.findViewById(R.id.imgContactConvo);
            contactName=(TextView) view.findViewById(R.id.nomContactConvo);
            textHint=(TextView) view.findViewById(R.id.texteApercu);
            timeStamp=(TextView) view.findViewById(R.id.timeStamp);
        }
    }


    public ConversationsAdapter(List<Conversation> convoList, Activity activity) {
        this.convoList = convoList;
        this.activity = activity;

        this.requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.default_avatar);
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
        Glide.with(activity).setDefaultRequestOptions(requestOptions).load(convo.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.avatar);
        holder.contactName.setText(convo.getContactName());
        holder.textHint.setText(convo.getTexthint());
        holder.timeStamp.setText(convo.getLastMessageDate());

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
