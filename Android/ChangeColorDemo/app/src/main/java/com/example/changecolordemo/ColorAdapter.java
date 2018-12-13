package com.example.changecolordemo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ColorAdapter extends ArrayAdapter {
    private final int resourceId;

    public ColorAdapter(Context context, int textViewResourceId, List<Item> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = (Item) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView colorImage = (ImageView)view.findViewById(R.id.color_item);
        TextView colorName=(TextView)view.findViewById(R.id.color_name);
        colorImage.setImageResource(item.getImageId());
        colorName.setText(item.getName());
        return view;
    }
}
