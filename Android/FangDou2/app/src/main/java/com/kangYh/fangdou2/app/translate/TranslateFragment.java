package com.kangYh.fangdou2.app.translate;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kangYh.fangdou2.Bean.TranslateResultBean;
import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.app.home.adapter.MsgAdapter;
import com.kangYh.fangdou2.app.home.api.TransApi;
import com.kangYh.fangdou2.app.home.model.Msg;
import com.kangYh.fangdou2.base.BaseFragment;
import com.kangYh.fangdou2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import crossoverone.statuslib.StatusUtil;


/**
 * Created by solo on 2018/1/8.
 */

public class TranslateFragment extends BaseFragment
{
    private List<Msg> msgList = new ArrayList<>();
    private static final String APP_ID = "20190807000324774";
    private static final String SECURITY_KEY = "W5lH8jJDKP_DiizMyqnm";
    private TransApi api;
    private MsgAdapter adapter;
    private RecyclerView msgRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        api = new TransApi(APP_ID, SECURITY_KEY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            view.setPadding(0, StatusUtil.getStatusBarHeight(getContext()), 0, 0);
        }


        init();

        final EditText inputText = (EditText) view.findViewById(R.id.input);
        Button sendBtn = (Button) view.findViewById(R.id.send);

        msgRecyclerView = (RecyclerView) view.findViewById(R.id.msg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String content = inputText.getText().toString();
                if ("".equals(content))
                {
                    ToastUtils.show("输入内容不能为空！");
                    return;
                }

                msgList.add(new Msg(content, Msg.TYPE.SENT));

                //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                int newSize = msgList.size() - 1;
                adapter.notifyItemInserted(newSize);
                msgRecyclerView.scrollToPosition(newSize);

                //清空输入框中的内容
                inputText.setText("");

                new TranslateTask().execute(content);

            }
        });
        return view;

    }


    private void init()
    {
        msgList.add(new Msg("在下面的对话框中输入你想翻译的话，就可以得到翻译后的结果啦。", Msg.TYPE.RECEIVED));

    }


    class TranslateTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = api.getTransResult(strings[0], "auto", "yue");
            Gson gson = new Gson();
            TranslateResultBean resultBean = gson.fromJson(result, TranslateResultBean.class);
            return resultBean.getTrans_result().get(0).getDst();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            msgList.add(new Msg(s, Msg.TYPE.RECEIVED));

            //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
            int newSize = msgList.size() - 1;
            adapter.notifyItemInserted(newSize);
            msgRecyclerView.scrollToPosition(newSize);
        }
    }


}

