package com.rt.sm.activity.mattress;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MattressPrepareActivity extends BaseActivity {


    @Override
    protected int bindLayout() {
        return R.layout.activity_mattress_prepare;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void goHead(){
        changeActivity(MattressConnectPowerActivity.class);
        finish();
    }


    @OnClick(R.id.back)
    void back(){
        finish();
    }





}
