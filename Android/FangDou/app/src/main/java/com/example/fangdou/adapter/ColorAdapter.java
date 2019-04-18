package com.example.fangdou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fangdou.Item;
import com.example.fangdou.R;

import java.util.List;


public class ColorAdapter extends ArrayAdapter
{
    private final int resourceId;

    public ColorAdapter(Context context, int textViewResourceId, List<Item> objects)
    {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Item item = (Item) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView colorImage = view.findViewById(R.id.color_item);
        TextView colorName = view.findViewById(R.id.color_name);
        colorImage.setImageResource(item.getImageId());
        colorName.setText(item.getName());
        return view;
    }
}
