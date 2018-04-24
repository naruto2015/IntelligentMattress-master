package com.rt.sm.activity;

import android.view.View;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_subhead)
    TextView tv_subhead;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @Override
    protected int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tv_title.setText("短信验证码已发送");
        tv_subhead.setText("13700000000");
        tv_subhead.setVisibility(View.VISIBLE);
        tv_right.setText("60s后可重新发送");
        tv_right.setVisibility(View.VISIBLE);
    }
}
