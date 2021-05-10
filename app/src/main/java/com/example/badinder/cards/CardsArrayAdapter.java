package com.example.badinder.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.badinder.R;
import com.example.badinder.cards.Card;

import org.w3c.dom.Text;

import java.util.List;

public class CardsArrayAdapter extends ArrayAdapter<Card> {

    private Context context;

    public CardsArrayAdapter(Context context, int resourceId, List<Card> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Card cardItem = getItem(position);

        if (convertView == null) convertView = LayoutInflater.from(
                getContext()).inflate(R.layout.item, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView interests = (TextView)  convertView.findViewById(R.id.card_interests);

        interests.setText("Кулинария Спорт");

        name.setText(cardItem.getName());
        switch (cardItem.getProfileImageUrl()) {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;

            default:
                Glide.with(convertView.getContext()).load(cardItem.getProfileImageUrl()).into(image);
                break;
        }


        return convertView;
    }

}
