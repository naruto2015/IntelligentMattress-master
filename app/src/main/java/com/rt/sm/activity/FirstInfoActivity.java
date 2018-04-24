package com.rt.sm.activity;

import android.view.View;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by haohw on 2018/1/2.
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

public class FirstInfoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_subhead)
    TextView tv_subhead;

    @Override
    protected int bindLayout() {
        return R.layout.activity_first_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tv_title.setText("添加基本信息");
        tv_subhead.setText("首次登陆，需要添加您的基本信息");
        tv_subhead.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
