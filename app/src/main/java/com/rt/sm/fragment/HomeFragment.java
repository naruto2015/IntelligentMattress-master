package com.rt.sm.fragment;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greendao.gen.MemberBeanDao;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.rt.sm.R;
import com.rt.sm.activity.mattress.AddMattressInfoActivity;
import com.rt.sm.activity.mattress.AjustMattressActivity;
import com.rt.sm.activity.mattress.MattessListActivity;
import com.rt.sm.activity.mattress.MattressPrepareActivity;
import com.rt.sm.activity.member.MemberSwitchActivity;
import com.rt.sm.bean.DataSynEvent;
import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseFragment;
import com.rt.sm.common.Constants;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.BleCommondUtils;
import com.rt.sm.utils.ClientManager;
import com.rt.sm.utils.GlideCircleTransform;
import com.rt.sm.utils.Logger;
import com.rt.sm.utils.SharedPreferenceHelper;
import com.rt.sm.utils.ToastUtils;
import com.rt.sm.view.BodyMoveView;
import com.rt.sm.view.CircleImageView;
import com.rt.sm.view.CircleTexView;
import com.rt.sm.view.HeartRateLine;
import com.rt.sm.view.ProgressView;
import com.rt.sm.view.RectTanglerView;
import com.rt.sm.view.SinView;
import com.rt.sm.view.SnorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;

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

public class HomeFragment extends BaseFragment {

    @BindView(R.id.man)
    CircleImageView man;

    @BindView(R.id.women)
    CircleImageView women;

    @BindView(R.id.mheadimg)
    CircleImageView mheadimg;

    @BindView(R.id.wheadimg)
    CircleImageView wheadimg;

    @BindView(R.id.mname)
    TextView mname;

    @BindView(R.id.wname)
    TextView wname;


    @BindView(R.id.add_layout)
    RelativeLayout add_layout;

    @BindView(R.id.adjust_layout)
    LinearLayout adjust_layout;

    @BindView(R.id.adjust_async_layout)
    RelativeLayout adjust_async_layout;

    @BindView(R.id.adjust_sync_layout)
    RelativeLayout adjust_sync_layout;


    @BindView(R.id.status_relayout)
    RelativeLayout status_relayout;

    @BindView(R.id.status_line)
    View status_line;

    @BindView(R.id.stallsView)
    ProgressView stallsView;

    @BindView(R.id.lHeart)
    HeartRateLine lHeart;

    @BindView(R.id.rHeart)
    HeartRateLine rHeart;

    @BindView(R.id.lbodyView)
    RectTanglerView lbodyView;

    @BindView(R.id.rbreatheView)
    SinView rbreatheView;

    @BindView(R.id.lbreathsinview)
    SinView lbreathsinview;

    @BindView(R.id.rbodyView)
    RectTanglerView rbodyView;

    @BindView(R.id.rsnorView)
    RectTanglerView rsnorView;

    @BindView(R.id.lsnorView)
    RectTanglerView lsnorView;

    @BindView(R.id.rSnorView)
    SnorView rSnorView;


    @BindView(R.id.lSnorView)
    SnorView lSnorView;




    private UUID mService;
    private UUID mWriteCharacter;
    private UUID mReadCharacter;
    private String write_uuid = "00003001-0606-0505-0404-030302020101";
    private String notify_uuid = "00003002-0606-0505-0404-030302020101";
    private String service = "00003000-0606-0505-0404-030302020101";
    private String address;


    private boolean mConnected;

    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.tv_lnum)
    TextView tv_lnum;

    @BindView(R.id.tv_rnum)
    TextView tv_rnum;

    @BindView(R.id.mattressName)
    TextView mattressName;

    @BindView(R.id.mattressModel)
    TextView mattressModel;

    @BindView(R.id.lbreathrate)
    TextView lbreathrate;

    @BindView(R.id.lheartrate)
    TextView lheartrate;


    @BindView(R.id.rheartrate)
    TextView rheartrate;

    @BindView(R.id.rbreathrate)
    TextView rbreathrate;


    @BindView(R.id.lbodyhrate)
    TextView lbodyhrate;

    @BindView(R.id.rbodyhrate)
    TextView rbodyhrate;


    @BindView(R.id.rbodyMoView)
    BodyMoveView rbodyMoView;


    @BindView(R.id.lbodyMoView)
    BodyMoveView lbodyMoView;

    //





    private String mattresno;

    private BluetoothAdapter mBluetoothAdapter;


    @Override
    public void onInvisible() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_1;
    }

    @Override
    public void initView(View view) {

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        mService = UUID.fromString(service);
        mWriteCharacter = UUID.fromString(write_uuid);
        mReadCharacter = UUID.fromString(notify_uuid);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        stallsView.setMyStalls(new ProgressView.MyStalls() {
            @Override
            public void getStalls(int stalls) {
            }

            @Override
            public void setStalls(int stalls) {
                Log.e("stalls",stalls+"");

                /*
                rHeart.setHeartHeight(0);
                lHeart.setHeartHeight(0);
                rbreatheView.setRectViewMove();
                lbreatheView.setRectViewMove();
                rsinview.setSinHeight(0);
                lsinview.setSinHeight(0);
                */
                byte[] command;
                if (stalls == 1) {
                    command = BleCommondUtils.stringToBytes("AA05C4000A");
                    ClientManager.getClient().write(address, mService, mWriteCharacter,
                            command, mWriteRsp);
                } else if (stalls == 2) {
                    command = BleCommondUtils.stringToBytes("AA05C4010A");
                    ClientManager.getClient().write(address, mService, mWriteCharacter,
                            command, mWriteRsp);
                } else if (stalls == 3) {
                    command = BleCommondUtils.stringToBytes("AA05C4020A");
                    ClientManager.getClient().write(address, mService, mWriteCharacter,
                            command, mWriteRsp);
                } else if (stalls == 4) {
                    command = BleCommondUtils.stringToBytes("AA05C4030A");
                    ClientManager.getClient().write(address, mService, mWriteCharacter,
                            command, mWriteRsp);
                } else if (stalls == 5) {
                    command = BleCommondUtils.stringToBytes("AA05C4040A");
                    ClientManager.getClient().write(address, mService, mWriteCharacter,
                            command, mWriteRsp);
                }


            }
        });
        //lHeart.setHeartHeight(0);
//        address = SharedPreferenceHelper.getAddress();
//        if(!TextUtils.isEmpty(address)){
//            ClientManager.getClient().
//
//                    registerConnectStatusListener(address, mConnectStatusListener);
//
//            connectDeviceIfNeeded();
//        }


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {



    }

    private  MemberBean mBean;


    private void getDefaultMattres(){

        List<MemberBean> memberBeans= DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        mBean=memberBeans.get(0);
        RetrofitUtils
                .getInstance(context)
                .api
                .getDefaultMattres(mBean.session_token)
                .compose(RxSchedulers.<ResultData<MattressBean>>compose())
                .subscribe(new BaseObserver<MattressBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MattressBean> resultData) {
                        ClientManager.getClient().disconnect(address);
                        ClientManager.getClient().unregisterConnectStatusListener(address, mConnectStatusListener);
                        if(resultData.status.equals("success")){
                            if(resultData.data!=null && resultData.data.mattresno!=null){
                                add_layout.setVisibility(View.GONE);
                                adjust_layout.setVisibility(View.GONE);
                                status_relayout.setVisibility(View.VISIBLE);
                                status_line.setVisibility(View.VISIBLE);
                                adjust_async_layout.setVisibility(View.GONE);
                                adjust_sync_layout.setVisibility(View.GONE);

                                if(!TextUtils.isEmpty(resultData.data.deviceserialno)){
                                    mattresno=resultData.data.mattresno;
                                    if(resultData.data.leftside!=null){
                                        String muser_head= RetrofitUtils.HEAD+mBean.getSession_token()+"&imagename="+resultData.data.leftside.avatar;
                                        Glide.with(context)
                                                .load(muser_head)
                                                .centerCrop() .error(R.drawable.no_head)
                                                .placeholder(R.drawable.no_head)
                                                .transform(new GlideCircleTransform(context))
                                                .into(man);
                                        Glide.with(context)
                                                .load(muser_head)
                                                .centerCrop() .error(R.drawable.no_head)
                                                .placeholder(R.drawable.no_head)
                                                .transform(new GlideCircleTransform(context))
                                                .into(mheadimg);
                                        mname.setText(resultData.data.leftside.householdname);

                                    }

                                    mattressName.setText(resultData.data.mattresname);
                                    mattressModel.setText("型号:"+resultData.data.model);
                                    if(resultData.data.rightside!=null){
                                        String wuser_head= RetrofitUtils.HEAD+mBean.getSession_token()+"&imagename="+resultData.data.rightside.avatar;
                                        Glide.with(context)
                                                .load(wuser_head)
                                                .centerCrop() .error(R.drawable.no_head)
                                                .placeholder(R.drawable.no_head)
                                                .transform(new GlideCircleTransform(context))
                                                .into(wheadimg);
                                        Glide.with(context)
                                                .load(wuser_head)
                                                .centerCrop() .error(R.drawable.no_head)
                                                .placeholder(R.drawable.no_head)
                                                .transform(new GlideCircleTransform(context))
                                                .into(wheadimg);
                                        wname.setText(resultData.data.rightside.householdname);
                                    }


                                    address=resultData.data.deviceserialno;
                                    // 判断设备是否打开蓝牙协议
                                    if (!mBluetoothAdapter.isEnabled()) {
                                        if (!mBluetoothAdapter.isEnabled()) {
                                            Intent enableBtIntent = new Intent(
                                                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                            startActivityForResult(enableBtIntent, Constants.BLE_REQUEST);
                                        }
                                    }

                                    ClientManager.getClient().

                                            registerConnectStatusListener(resultData.data.deviceserialno, mConnectStatusListener);

                                    connectDeviceIfNeeded();
                                }

                            }

                        }


                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        /*
        address = SharedPreferenceHelper.getAddress();
        if(!TextUtils.isEmpty(address)){
            ClientManager.getClient().

                    registerConnectStatusListener(address, mConnectStatusListener);

            connectDeviceIfNeeded();
        }
       */
        getDefaultMattres();

    }


    @OnClick(R.id.man)
    public void man(){

        Intent intent=new Intent(getActivity(),MemberSwitchActivity.class);
        intent.putExtra("mattresno",mattresno);
        intent.putExtra("side","left");
        getActivity().startActivity(intent);

    }


    @OnClick(R.id.women)
    public void women(){

        Intent intent=new Intent(getActivity(),MemberSwitchActivity.class);
        intent.putExtra("mattresno",mattresno);
        intent.putExtra("side","right");
        getActivity().startActivity(intent);
    }


    @OnClick(R.id.mheadimg)
    public void mheadimg(){

        Intent intent=new Intent(getActivity(),MemberSwitchActivity.class);
        intent.putExtra("mattresno",mattresno);
        intent.putExtra("side","left");
        getActivity().startActivity(intent);

    }


    @OnClick(R.id.wheadimg)
    public void wheadimg(){

        Intent intent=new Intent(getActivity(),MemberSwitchActivity.class);
        intent.putExtra("mattresno",mattresno);
        intent.putExtra("side","right");
        getActivity().startActivity(intent);
    }




    @OnClick(R.id.iv_add)
    public void add(){

        //add_layout.setVisibility(View.GONE);
//        Intent intent=new Intent(getActivity(), AjustMattressActivity.class);
//        getActivity().startActivity(intent);
        changeActivity(MattressPrepareActivity.class);
//        add_layout.setVisibility(View.GONE);
//        adjust_layout.setVisibility(View.VISIBLE);

    }


    @OnClick(R.id.tv_sync)
    public void ajustSync(){

        add_layout.setVisibility(View.GONE);
        adjust_async_layout.setVisibility(View.GONE);
        adjust_sync_layout.setVisibility(View.VISIBLE);


    }

    @OnClick(R.id.aloneAdjustL)
    public void ajustL(){

        add_layout.setVisibility(View.GONE);
        adjust_async_layout.setVisibility(View.VISIBLE);
        adjust_sync_layout.setVisibility(View.GONE);


    }


    @OnClick(R.id.aloneAdjustR)
    public void ajustR(){

        add_layout.setVisibility(View.GONE);
        adjust_async_layout.setVisibility(View.VISIBLE);
        adjust_sync_layout.setVisibility(View.GONE);


    }

    @OnClick(R.id. btn_ajust_mattress)
    public void  btnajustmattress(){


        add_layout.setVisibility(View.GONE);
        adjust_layout.setVisibility(View.VISIBLE);
        status_relayout.setVisibility(View.GONE);
        status_line.setVisibility(View.GONE);
        adjust_async_layout.setVisibility(View.VISIBLE);
        adjust_sync_layout.setVisibility(View.GONE);



//       int i= (int) (Math.random()*254+1);
//       rbodyMoView.setData(i);
        //rSnorView.setSnorData(0);


    }



    @OnClick(R.id. check_status)
    public void  checkstatus(){

        add_layout.setVisibility(View.GONE);
        adjust_layout.setVisibility(View.GONE);
        status_relayout.setVisibility(View.VISIBLE);
        status_line.setVisibility(View.VISIBLE);
        adjust_async_layout.setVisibility(View.GONE);
        adjust_sync_layout.setVisibility(View.GONE);


    }


    @OnClick(R.id.rrise)
    public void rrise() {
        byte[] command = BleCommondUtils.stringToBytes("AA06C700010A");
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);

    }

    @OnClick(R.id.lrise)
    public void lrise() {
        byte[] command = BleCommondUtils.stringToBytes("AA06C701000A");
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);

    }


    @OnClick(R.id.rdrop)
    public void rdrop() {

        byte[] command = BleCommondUtils.stringToBytes("AA06C700020A");
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);
    }

    @OnClick(R.id.ldrop)
    public void ldrop() {


        byte[] command = BleCommondUtils.stringToBytes("AA06C702000A");
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);
    }


    @OnClick(R.id.rhardness_plus)
    public void rhardness_plus() {

        int num = Integer.parseInt(tv_lnum.getText().toString());
        int num2 = Integer.parseInt(tv_rnum.getText().toString());

        if (num2 < 100) {
            num2 = num2 + 5;
            tv_rnum.setText(num2 + "");
            Log.i("num", "num: " + BleCommondUtils.numToHex8(num2));

            byte[] command = BleCommondUtils.stringToBytes("AA06C5" + BleCommondUtils.numToHex8(num) +
                    BleCommondUtils.numToHex8(num2) + "0A");
            ClientManager.getClient().write(address, mService, mWriteCharacter,
                    command, mWriteRsp);
        } else {
            ToastUtils.showShortMsg("硬度已到最大值");
        }
    }


    @OnClick(R.id.lhardness_cut)
    public void lhardness_cut() {
        int num = Integer.parseInt(tv_lnum.getText().toString());
        int num2 = Integer.parseInt(tv_rnum.getText().toString());

        if (num > 5) {
            num = num - 5;
            tv_lnum.setText(num + "");
            Log.i("num", "num: " + BleCommondUtils.numToHex8(num));

            byte[] command = BleCommondUtils.stringToBytes("AA06C5" + BleCommondUtils.numToHex8(num) +
                    BleCommondUtils.numToHex8(num2) + "0A");
            ClientManager.getClient().write(address, mService, mWriteCharacter,
                    command, mWriteRsp);
        } else {
            ToastUtils.showShortMsg("硬度已到最小值");
        }
    }


    @OnClick(R.id.lhardness_plus)
    public void lhardness_plus() {
        int num = Integer.parseInt(tv_lnum.getText().toString());
        int num2 = Integer.parseInt(tv_rnum.getText().toString());

        if (num < 100) {
            num = num + 5;
            tv_lnum.setText(num + "");
            Log.i("num", "num: " + BleCommondUtils.numToHex8(num));

            byte[] command = BleCommondUtils.stringToBytes("AA06C5" + BleCommondUtils.numToHex8(num) +
                    BleCommondUtils.numToHex8(num2) + "0A");
            ClientManager.getClient().write(address, mService, mWriteCharacter,
                    command, mWriteRsp);
        } else {
            ToastUtils.showShortMsg("硬度已到最大值");
        }

    }


    @OnClick(R.id.rhardness_cut)
    public void rhardness_cut() {

        int num = Integer.parseInt(tv_lnum.getText().toString());
        int num2 = Integer.parseInt(tv_rnum.getText().toString());

        if (num2 > 5) {
            num2 = num2 - 5;
            tv_rnum.setText(num2 + "");
            Log.i("num", "num: " + BleCommondUtils.numToHex8(num2));

            byte[] command = BleCommondUtils.stringToBytes("AA06C5" + BleCommondUtils.numToHex8(num) +
                    BleCommondUtils.numToHex8(num2) + "0A");
            ClientManager.getClient().write(address, mService, mWriteCharacter,
                    command, mWriteRsp);
        } else {
            ToastUtils.showShortMsg("硬度已到最小值");
        }

    }

    @OnClick(R.id.syncrise)
    public void syncrise() {
        byte[] command = BleCommondUtils.stringToBytes("AA06C701010A");
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);

    }


    @OnClick(R.id.syncdrop)
    public void syncdrop() {
        byte[] command = BleCommondUtils.stringToBytes("AA06C702020A");
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);

    }

    @OnClick(R.id.mattressName)
    public void mattressName() {
        tv_state.setText("未连接");
        ClientManager.getClient().disconnect(address);
        ClientManager.getClient().unregisterConnectStatusListener(address, mConnectStatusListener);
        changeActivity(AjustMattressActivity.class);

    }





    @Override
    public void onPause() {
        RxSchedulers.clear();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        RxSchedulers.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void result(DataSynEvent dataSynEvent) {
        //添加床垫成功
        if (dataSynEvent.getMsgType() == 1) {
            add_layout.setVisibility(View.GONE);
            adjust_layout.setVisibility(View.VISIBLE);

        }

    }


    private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            BluetoothLog.v(String.format("DeviceDetailActivity onConnectStatusChanged %d in %s",
                    status, Thread.currentThread().getName()));

            mConnected = (status == STATUS_CONNECTED);
            if (!mConnected) {
                tv_state.setText("蓝牙已断开");
            }
            connectDeviceIfNeeded();
        }
    };

    private void connectDeviceIfNeeded() {
        if (!mConnected) {
            connectDevice();
        }
    }

    private void connectDevice() {
        tv_state.setText("正在连接");

        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(20000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        ClientManager.getClient().connect(address, options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                BluetoothLog.v(String.format("profile:\n%s", profile));
                if (code == REQUEST_SUCCESS) {
                    tv_state.setText("蓝牙已连接");
                    readData();
                }
            }
        });
    }

    /**
     * 获取ble主动传过来的信息
     */
    private void readData() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ClientManager.getClient().notify(address, mService, mReadCharacter, mNotifyRsp);
            }
        }, 2000);

    }

    private final BleNotifyResponse mNotifyRsp = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            if (service.equals(mService) && character.equals(mReadCharacter)) {
                //ToastUtils.showShortMsg(String.format("%s", ByteUtils.byteToString(value)));
                Logger.i("data", String.format("%s", ByteUtils.byteToString(value)));

                if(value !=null && value.length>3){
                    int hex=value[3];
                    if(hex==1){
                        int heartHex=value[12];
                        lheartrate.setText(heartHex+"次/分钟");
                        if(heartHex>0){
                            lHeart.setHeartHeight(0);

                        }
                        int breatthHex=value[13];
                        lbreathrate.setText(breatthHex+"次/分钟");
                        if(breatthHex>0){
                            lbreathsinview.setSinHeight(0);

                        }



                        int lsnorHex=value[14];
                        if(lsnorHex>0){
                            //lsnorView.setRectViewMove();
                            lSnorView.setSnorData(0);
                        }



                    }

                    if (hex==3){
                        int heartHex=value[9];
                        rheartrate.setText(heartHex+"次/分钟");
                        if(heartHex>0){
                            rHeart.setHeartHeight(0);

                        }
                        int breatthHex=value[10];
                        rbreathrate.setText(breatthHex+"次/分钟");
                        if(breatthHex>0){

                            rbreatheView.setSinHeight(0);

                        }

                        int lbodyHex=value[4];
                        lbodyhrate.setText(lbodyHex+"");
                        if(lbodyHex>0){
                            //lbodyView.setRectViewMove();
                            lbodyMoView.setData(lbodyHex);

                        }

                        int rbodyHex=value[15];
                        rbodyhrate.setText(rbodyHex+"");
                        if(rbodyHex>0){
                            //rbodyView.setRectViewMove();
                            rbodyMoView.setData(rbodyHex);

                        }

                        int rsnorHex=value[11];
                        if(rsnorHex>0){
                            //rsnorView.setRectViewMove();
                            rSnorView.setSnorData(0);
                        }

                       // Logger.e("rtest","heartHex"+heartHex+"breatthHex"+breatthHex+"rsinHex"+rsinHex+"rsnorHex"+rsnorHex);



                    }

                }


            }


        }

        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                Logger.i("notify", "success");
            } else {
                Logger.i("notify", "failed");
            }
        }
    };


    @Override
    public void onDestroyView() {
        ClientManager.getClient().disconnect(address);
        ClientManager.getClient().unregisterConnectStatusListener(address, mConnectStatusListener);
        super.onDestroyView();
    }


    private final BleWriteResponse mWriteRsp = new BleWriteResponse() {
        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                ToastUtils.showShortMsg("发送成功");
            } else {
                ToastUtils.showShortMsg("发送失败");
            }
        }
    };




}
