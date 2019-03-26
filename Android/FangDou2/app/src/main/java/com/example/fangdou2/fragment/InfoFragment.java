package com.example.fangdou2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.fangdou2.R;

import java.util.Map;

public class InfoFragment extends Fragment implements View.OnClickListener
{
    private View view;
    private WebView webView;
    private Button exitButton;
    private TextView textView;
    private WebSettings webSettings;
    private Fragment fragment;
    private MapFragment mapFragment;
    private String title, url;

    public InfoFragment(String title, String url)
    {
        this.title = title;
        this.url = url;
    }

    public InfoFragment()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
            {
                parent.removeView(view);
            }

        } else
        {
            view = inflater.inflate(R.layout.infocard, null);
        }
        initView();


        return view;
    }

    @Override
    public void onClick(View v)
    {
        fragment = InfoFragment.this.getParentFragment();
        mapFragment = (MapFragment) fragment;
        if (v.getId() == R.id.exitButton)
        {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    public void initView()
    {
        webView = (WebView) view.findViewById(R.id.webView);
        textView = (TextView) view.findViewById(R.id.infoTitle);
        exitButton = (Button) view.findViewById(R.id.exitButton);
        webSettings = webView.getSettings();
        textView.setText(title);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        exitButton.setOnClickListener(this);


    }

   /* @Override
    public void onDestroyView()
    {
        if (webView != null)
        {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;

        }
        super.onDestroyView();

    }*/
}
