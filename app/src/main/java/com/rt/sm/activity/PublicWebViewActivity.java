package com.rt.sm.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicWebViewActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.title)
    TextView title;

    @Override
    protected int bindLayout() {
        return R.layout.activity_public_web_view;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        title.setText(getIntent().getStringExtra("title"));

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl(getIntent().getStringExtra("url"));


    }


    @OnClick(R.id.back)
    void back(){
        finish();
    }




}
