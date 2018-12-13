package com.example.qq1296821114.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qq1296821114.Data.Bean.Letter;
import com.example.qq1296821114.Data.DataBase.MyDB;
import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Adapter.SquareRecycleAdapter;
import com.example.qq1296821114.Utils.DBUtil;
import com.example.qq1296821114.View.Dialog.Test_Dialog;

import java.util.ArrayList;

public class SquareFragment extends Fragment {

    ArrayList<Letter> dataArray;
    SquareRecycleAdapter squareRecycleAdapter;
    RecyclerView recyclerView;
    Button button;
    private MyDB myDB;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ArrayList<Letter> arrayList = new ArrayList<>(DBUtil.getLetter(myDB, null, getContext()));
                    if (!arrayList.isEmpty())
                        for (Letter letter : arrayList) {
                            letter.ii(getContext());
                            dataArray.add(letter);
                        }
                    squareRecycleAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._square_layout, container, false);

        myDB = MyDB.getMyDB(getContext());
        init_view(view);
        return view;
    }

    private void init_view(View view) {
        dataArray = new ArrayList<>();

        squareRecycleAdapter = new SquareRecycleAdapter(dataArray, new SquareRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Test_Dialog test_dialog = new Test_Dialog(getContext(), dataArray.get(position).getPicture());
                test_dialog.show();
            }
        });

        button = view.findViewById(R.id.my_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtil.getLetter(myDB, new DBUtil.DBUtilListener() {
                    @Override
                    public void loadSuccess() {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void loadFailure() {

                    }
                }, getContext());
            }
        });
        recyclerView = view.findViewById(R.id._square_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(squareRecycleAdapter);
        squareRecycleAdapter.notifyDataSetChanged();

    }

}
