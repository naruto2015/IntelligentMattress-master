package com.rt.sm.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class AboutActivity extends BaseActivity {

    private RxPermissions rxPermissions;

    @BindView(R.id.phone)
    TextView phone;

    @Override
    protected int bindLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);

    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }


    @OnClick(R.id.call_layout)
    void callup() {
        rxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            callPhone();
                        }
                    }
                });

    }


    public void callPhone() {



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String phoneNum= phone.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

}
