package com.rt.sm.fragment;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rt.sm.R;
import com.rt.sm.common.BaseFragment;
import com.rt.sm.internet.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by haohw on 2018/1/7.
 * <p>
 * ----------Dragon be here!----------/
 * 　　　┏┓　　 ┏┓
 * 　　┏┛┻━━━┛┻┓━━━
 * 　　┃　　　　　 ┃
 * 　　┃　　　━　  ┃
 * 　　┃　┳┛　┗┳
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　  ┃
 * 　　┃　　　　   ┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */

public class StatisticsFragment extends BaseFragment {


    @BindView(R.id.webview)
    WebView webView;

    @Override
    public void onInvisible() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_3;
    }

    @Override
    public void initView(View view) {

        ButterKnife.bind(this, view);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl(RetrofitUtils.STATISTICS);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}