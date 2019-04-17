package com.example.fangdou2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fangdou2.R;

public class InfoFragment extends Fragment implements View.OnClickListener
{
    private View view;
    private WebView webView;
    private ImageButton exitButton;
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
            view = inflater.inflate(R.layout.fragment_infocard, null);
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
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.translate_out, R.anim.translate_out)
                    .remove(this).commit();
        }
    }

    public void initView()
    {
        webView = view.findViewById(R.id.webView);
        textView = view.findViewById(R.id.infoTitle);
        exitButton = view.findViewById(R.id.exitButton);
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
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

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
