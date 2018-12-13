package com.example.qq1296821114.Utils.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qq1296821114.Data.Bean.Letter;
import com.example.qq1296821114.R;

import java.util.ArrayList;

import jp.wasabeef.richeditor.RichEditor;

public class SquareRecycleAdapter extends RecyclerView.Adapter<SquareRecycleAdapter.MyHolder> {

    private ArrayList<Letter> data_array;
    private OnItemClickListener onItemClickListener;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick((Integer) view.getTag());
        }
    };

    public SquareRecycleAdapter(ArrayList<Letter> data_array, OnItemClickListener onItemClickListener) {
        this.data_array = data_array;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._square_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        view.setOnClickListener(clickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(data_array.get(position).getHead());
        holder.itemView.setTag(position);
        holder.richEditor.setHtml(data_array.get(position).getHtml());
    }

    @Override
    public int getItemCount() {
        return data_array.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        //实现的方法
        private TextView textView;
        private RichEditor richEditor;

        MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id._square_text);
            richEditor=itemView.findViewById(R.id._square_text1);

            richEditor.setEditorHeight(200);
            richEditor.setEditorFontSize(22);
            richEditor.setEditorFontColor(Color.BLACK);
            richEditor.setPadding(10, 10, 10, 10);
//        mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
            richEditor.setInputEnabled(false);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
