package com.rt.sm.internet;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.rt.sm.bean.ResultData;
import com.rt.sm.dialog.LoadingDialog;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by gaodesong on 17/9/4.
 */

public abstract class BaseObserver<T> implements Observer<ResultData<T>> {


    private Context mContext;
    private LoadingDialog loadingDialog=null;

    public BaseObserver(Context context, boolean flag){
        this.mContext=context.getApplicationContext();
        if(flag){
             //进度框
            loadingDialog=new LoadingDialog(context);
        }

    }


    @Override
    public void onSubscribe(Disposable d) {

        Log.e("tag","onSubscribe");

    }



    @Override
    public void onNext(ResultData<T> resultData) {


        if (loadingDialog!=null){
            loadingDialog.dismiss();
        }

        onHandlerSuccess(resultData);



    }

    @Override
    public void onError(Throwable e) {
        //Log.e("BaseObserver", "error:" + e.toString());
        Log.e("BaseObserver", "error:" +Thread.currentThread().getName());
        if (loadingDialog!=null){
            loadingDialog.dismiss();
        }
        try {

            if (e instanceof SocketTimeoutException) {

                Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();

            } else if (e instanceof ConnectException || e instanceof ConnectTimeoutException) {

                Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();

            } else {

                //Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e1){


            e1.printStackTrace();

        }


    }







    @Override
    public void onComplete() {
        Log.e("tag","onComplete");

    }


    public abstract void onHandlerSuccess(ResultData<T> resultData);




}
