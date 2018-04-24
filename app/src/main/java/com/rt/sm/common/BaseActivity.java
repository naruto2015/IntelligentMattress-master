package com.rt.sm.common;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.rt.sm.internet.RxSchedulers;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by 郝宏伟 on 2016/10/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private InputMethodManager manager;

    protected abstract int bindLayout();

    protected abstract void initView();

    //是否沉浸式状态栏
    private boolean isSetStatusBar = true;
    //等待提示框

    public Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());

        context=this;
        initView();
        //steepStatusBar();
        //不知道是干嘛的，注掉再说
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //华为适配
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }
//
//        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onPause() {
        RxSchedulers.clear();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        RxSchedulers.clear();
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 点击软键盘外部隐藏软键盘
     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(ev.getAction() == MotionEvent.ACTION_DOWN){
//            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
//                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//    /**
//     * [沉浸状态栏]
//     */
    private void steepStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.translucentStatusBar(this);
        }
    }

    /**
     * 跳转界面
     */
    public void changeActivity(Class<?> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
       /* overridePendingTransition(R.anim.in_right, R.anim.out_left);*/
    }




    /**
     * 跳转界面,带参数
     */
    public void changeActivity(Class<?> clz, Bundle bundle) {

        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面(有结果返回)
     */
    public void changeActivityForResult(Class<?> clz, int code, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, code);
    }

    /**
     * 跳转界面(有结果返回)
     */
    public void changeActivityForResult(Class<?> clz, int code) {
        Intent intent = new Intent(this, clz);
        startActivityForResult(intent, code);
    }


    /**
     * 选择框基本工具
     *
     * @param chose
     * @param tv
     * @param title
     */
    public static void ShowChoise(Context context, final String[] chose, final TextView tv, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);

        builder.setTitle(title);

        //    设置一个下拉的列表选择项
        builder.setItems(chose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv.setText(chose[which]);
            }
        });
        builder.show();
    }


    public void setSetStatusBar() {
        isSetStatusBar = false;
    }





}
