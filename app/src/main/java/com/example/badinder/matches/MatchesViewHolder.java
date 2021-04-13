package com.example.badinder.matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.badinder.R;
import com.example.badinder.chat.ChatActivity;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mLastMessage, mMatchName;

    public ImageView mMatchImage;

    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mLastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
        mMatchName = (TextView) itemView.findViewById(R.id.matchName);

        mMatchImage = (ImageView) itemView.findViewById(R.id.matchImage);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("lastMessage", mLastMessage.getText().toString());
        intent.putExtras(bundle);
        v.getContext().startActivity(intent);
    }
}
