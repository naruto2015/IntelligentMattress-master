package com.rt.sm.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.rt.sm.R;
import com.rt.sm.view.ChrysanthemumView;


/**
 * Created by gaodesong on 17/7/14.
 */

public class LoadingDialog {


    public  android.app.AlertDialog ad;

    public LoadingDialog(Context context){
        ad=new AlertDialog
                .Builder(context, R.style.dialog_loading)
                .create();
        Window window = ad.getWindow();
        ad.show();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.load_alert_dialog,null);
        window.setContentView(view);
    }


    public  void dismiss() {

        if(ad != null && ad.isShowing()){
            ad.dismiss();
        }


    }













}
