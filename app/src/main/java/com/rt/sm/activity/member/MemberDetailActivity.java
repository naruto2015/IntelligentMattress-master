package com.rt.sm.activity.member;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.bumptech.glide.Glide;
import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.BitmapUtil;
import com.rt.sm.utils.GlideCircleTransform;
import com.rt.sm.utils.ToastUtils;
import com.rt.sm.view.CircleImageView;
import com.rt.sm.view.CircleTexView;
import com.rt.sm.view.SwitchView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MemberDetailActivity extends BaseActivity {


    @BindView(R.id.sex)
    SwitchView switchView;

    public static int REQUEST_LIBRARY_PHOTO = 1;

    public static int REQUEST_TAKE_PHOTO = 2;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    private Bitmap headImg = null;

    private String headImgOriFileName;

    private RxPermissions rxPermissions;


    @BindView(R.id.tv_birth)
    TextView tv_birth;

    @BindView(R.id.et_blood)
    EditText et_blood;


    @BindView(R.id.et_hieght)
    EditText et_hieght;


    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.et_weight)
    EditText et_weight;

    private String session_token;

    private String householdacc;

    private MemberBean memberBean;

    @Override
    protected int bindLayout() {
        return R.layout.activity_member_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        initData();

    }

    private void initData() {

        memberBean=new MemberBean();
        memberBean= DBHelper.getDaoSession().getMemberBeanDao().loadAll().get(0);
        session_token=memberBean.getSession_token();

        Bundle bundle=getIntent().getExtras();
//        householdacc=bundle.getString("householdacc");
        String birthday=bundle.getString("birthday");
//        householdacc=bundle.getString("avatar");
//        householdacc=bundle.getString("sex");
        String bodyheight=bundle.getString("bodyheight");
        String bodyweight=bundle.getString("bodyweight");
        String bloodtype=bundle.getString("bloodtype");
        String householdname=bundle.getString("householdname");
        memberBean.useracc=bundle.getString("useracc");
        memberBean.householdacc=bundle.getString("householdacc");



        tv_birth.setText(birthday==null?"":birthday);
        et_blood.setText(bloodtype==null?"":bloodtype);
        et_hieght.setText(bodyheight==null?"":bodyheight);
        et_weight.setText(bodyweight==null?"":bodyweight);
        et_name.setText(householdname==null?"":householdname);

        int sex=bundle.getInt("sex");
        if(sex==1){
            switchView.setSex("男");
        }else {
            switchView.setSex("女");
        }

        String user_head= RetrofitUtils.HEAD+memberBean.getSession_token()+"&imagename="+bundle.getString("avatar");

        Glide.with(context)
                .load(user_head)
                .centerCrop() .error(R.drawable.no_head)
                .placeholder(R.drawable.no_head)
                .transform(new GlideCircleTransform(context))
                .into(profile_image);



        /*
        RetrofitUtils.getInstance(context)
                .api
                .getOneSleepMember(session_token,householdacc)
                .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                .subscribe(new BaseObserver<MemberBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                        if(resultData.status.equals("success")){

                            tv_birth.setText(resultData.data.birthday+"");
                            et_blood.setText(resultData.data.bloodtype+"");
                            et_hieght.setText(resultData.data.bodyheight+"");
                            et_weight.setText(resultData.data.bodyweight+"");
                            et_name.setText(resultData.data.householdname+"");
                            //String sex=resultData.data.sex;
                            int sex=resultData.data.sex;
                            if(sex==1){
                                switchView.setSex("男");
                            }else {
                                switchView.setSex("女");
                            }

                        }else {


                        }


                    }
                });

*/
    }


    @OnClick(R.id.tv_birth)
    void tv_birth(){

//        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int iyear, int monthOfYear, int dayOfMonth) {
//
////                long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
////                int year = datePicker.getYear();//年
////                int month = datePicker.getMonth();//月-1
////                int dayOfMonth1 = datePicker.getDayOfMonth();
//                tv_birth.setText(iyear+"-"+monthOfYear+"-"+dayOfMonth);
//
//           }
//       }, 1970, 0, 1);//2013:初始年份，2：初始月份-1 ，1：初始日期
//       dp.show();

        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                  tv_birth.setText(year+"-"+month+"-"+day);
            }
        }).textConfirm("确定") //text of confirm button
                .textCancel("取消") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(getResources().getColor(R.color.colorGrayLine)) //color of cancel button
                .colorConfirm(getResources().getColor(R.color.colorTvH))//color of confirm button
                .minYear(1900) //min year in loop
                .maxYear(2550) // max year in loop
                .showDayMonthYear(false) // shows like dd mm yyyy (default is false)
                .dateChose("2013-11-11") // date chose when init popwindow
                .build();

        pickerPopWin.showPopWin(this);




    }

    @OnClick(R.id.sex)
    void setSex() {

        if(switchView.getSelected().equals("男")){
            switchView.setSex("女");
        }else {
            switchView.setSex("男");
        }

    }


    @OnClick(R.id.profile_image)
    void profileimage(){

        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            openDialog();
                        }
                    }
                });



    }


    private void openDialog(){
        NormalSelectionDialog dialog=new NormalSelectionDialog.Builder(this)
                .setlTitleVisible(false)   //设置是否显示标题
                .setTitleHeight(65)   //设置标题高度
                .setTitleText("please select")  //设置标题提示文本
                .setTitleTextSize(14) //设置标题字体大小 sp
                .setTitleTextColor(R.color.colorPrimary) //设置标题文本颜色
                .setItemHeight(40)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(14)  //设置item字体大小
                .setCancleButtonText("取消")  //设置最底部钮文本
                .setOnItemListener(new com.wevey.selector.dialog.DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                        dialog.dismiss();
                        if(position==0){
                            MemberDetailActivity.this.startActivityForResult(new Intent(
                                            "android.intent.action.PICK",
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                                    MemberDetailActivity.this.REQUEST_LIBRARY_PHOTO);

                        }else if(position==1){
                            capture();
                        }
                    }
                }).setCanceledOnTouchOutside(true)
                .build();
        ArrayList<String> s = new ArrayList<>();
        s.add("从本地选择图片");
        s.add("拍照");
        dialog.setDatas(s);
        dialog.show();
    }

    private void capture(){
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            MemberDetailActivity.this.startActivityForResult(intent,
                                    MemberDetailActivity.this.REQUEST_TAKE_PHOTO);
                        }
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri mImageCaptureUri;
            if (requestCode == 2 || requestCode == 1)// 原图
            {
                if (headImg != null)
                    headImg.recycle();
                mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    try {
                        headImg = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(), mImageCaptureUri);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        //Log.e(TAG, e.getMessage());
                    }
                } else {
                    Bundle extras = data.getExtras();
                    headImg = extras.getParcelable("data");
                    mImageCaptureUri = Uri.parse(MediaStore.Images.Media
                            .insertImage(getContentResolver(), headImg, null,
                                    null));
                }
                Intent intent = new Intent();
                intent.setAction("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");// mUri是已经选择的图片Uri
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);// 裁剪框比例
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 120);// 输出图片大小
                intent.putExtra("outputY", 120);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 200);
            } else if (requestCode == 200)// 剪裁完成的图
            {
                //headImgOriFileName = Environment.getExternalStorageDirectory()+ File.separator+"dqysh"+random.nextInt(10000) + ".jpg";



                headImgOriFileName=System.currentTimeMillis() + ".jpg";
                mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    try {
                        String[] proj = { MediaStore.Images.Media.DATA };
                        Cursor actualimagecursor = managedQuery(mImageCaptureUri,proj,null,null,null);
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(actual_image_column_index);
//			    	File file = new File(img_path);
                        BitmapUtil.compress2(MemberDetailActivity.this,img_path);
                        headImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageCaptureUri);
//    			headImg = MediaStore.Images.Media.getBitmap(
//    				this.getContentResolver(), mImageCaptureUri);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        //Log.e(TAG, e.getMessage());
                    }
                } else {
                    headImg = data.getExtras().getParcelable("data");
                }
                headImgOriFileName=savePicToSdcard(headImg, headImgOriFileName);
                profile_image.setImageBitmap(headImg);
                profile_image.setVisibility(View.VISIBLE);

            }

        }
    }


    public static String savePicToSdcard(Bitmap bitmap, String fileName) {
        if (bitmap == null||bitmap.isRecycled()) {
            return "";
        } else {
            // File destFile = new
            // File(Environment.getExternalStorageDirectory()+"/dqysh",fileName);
            File appDir=new File(Environment.getExternalStorageDirectory(),"lovefamily");
            if(!appDir.exists()){
                appDir.mkdir();
            }
            File destFile = new File(appDir,fileName);
            fileName=destFile.getAbsolutePath();
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);// 100代表不压缩
                os.flush();
                os.close();
            } catch (IOException e) {

                fileName = "";
            }
        }
        return fileName;
    }




    @OnClick(R.id.icon_delete)
    void icon_delete(){

        RetrofitUtils.getInstance(context)
                .api
                .deletehousehold(session_token,householdacc)
                .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                .subscribe(new BaseObserver<MemberBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                        if(resultData.status.equals("success")){

                            finish();

                        }else {

                            ToastUtils.showShortMsg("删除失败");

                        }


                    }
                });
    }


    @OnClick(R.id.btn_update)
    void btn_update(){

        memberBean.householdname=et_name.getText().toString();
        memberBean.birthday=tv_birth.getText().toString();
        memberBean.bodyweight=et_weight.getText().toString();
        memberBean.bodyheight=et_hieght.getText().toString();
        memberBean.bloodtype=et_blood.getText().toString();

        //memberBean.avatar="";

        if (switchView.getSelected().equals("男")){
            memberBean.sex=1;
        }else {
            memberBean.sex=0;
        }
        if(!TextUtils.isEmpty(headImgOriFileName)){
            File file = new File(headImgOriFileName);

            //构建body
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    //.addFormDataPart("sessiontoken", memberBean.session_token)
                    .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                    .build();

            RetrofitUtils
                    .getInstance(context)
                    .api
                    .uploadImage(memberBean.session_token,requestBody)
                    .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                    .subscribe(new BaseObserver<MemberBean>(context,true) {
                        @Override
                        public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                            if(resultData.status.equals("success")){
                                memberBean.avatar=resultData.data.imagename;
                                updateUserInfor();

                            }


                        }
                    });
        }else {
            updateUserInfor();
        }


    }

    private void updateUserInfor() {

        RetrofitUtils.getInstance(context)
                .api
                .updateHousehold(memberBean.session_token,memberBean)
                .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                .subscribe(new BaseObserver<MemberBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                        if(resultData.status.equals("success")){
                            finish();
                        }


                    }
                });
    }


    @OnClick(R.id.back)
    void back(){
        finish();
    }





}
