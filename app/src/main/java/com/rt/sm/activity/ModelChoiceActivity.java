package com.rt.sm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModelChoiceActivity extends BaseActivity {


    @Override
    protected int bindLayout() {
        return R.layout.activity_model_choice;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }

}
