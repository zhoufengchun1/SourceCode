package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit>
{
    private int resourcesId;

    public FruitAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Fruit> objects)
    {
        super(context, textViewResourceId, objects);
        resourcesId=textViewResourceId;
    }
/*
    @NonNull
    @Override
   public View getView(int position,View convertView,ViewGroup parent)
    {
        Fruit fruit = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourcesId, parent, false);
    }*/
}