package com.rt.sm.activity.mattress;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.utils.BleCommondUtils;
import com.rt.sm.utils.ClientManager;
import com.rt.sm.utils.Logger;
import com.rt.sm.utils.SharedPreferenceHelper;
import com.rt.sm.utils.TimeUtil;
import com.rt.sm.utils.ToastUtils;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;

public class AddMatressWifiActivity extends BaseActivity {

    @BindView(R.id.wifiName)
    TextView wifiName;
    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.prompt1)
    TextView prompt1;
    @BindView(R.id.prompt2)
    TextView prompt2;

    private UUID mService;
    private UUID mWriteCharacter;
    private UUID mReadCharacter;
    private String write_uuid = "00003001-0606-0505-0404-030302020101";
    private String notify_uuid = "00003002-0606-0505-0404-030302020101";
    private String service = "00003000-0606-0505-0404-030302020101";
    private String address;

    private String nn, nb, pp, pb;

    private boolean mConnected;


    @Override
    protected int bindLayout() {
        return R.layout.activity_add_matress_wifi;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.e("wifi信息", wifiInfo.getSSID() + ";" + wifiInfo.toString());
//        System.out.println("wifi信息："+wifiInfo.toString());
//        System.out.println("wifi名称："+wifiInfo.getSSID());
        String name = wifiInfo.getSSID().replaceAll("\"", "");
        wifiName.setText(name);


        mService = UUID.fromString(service);
        mWriteCharacter = UUID.fromString(write_uuid);
        mReadCharacter = UUID.fromString(notify_uuid);
        address = SharedPreferenceHelper.getAddress();

        if(!TextUtils.isEmpty(address)){
            ClientManager.getClient().

                    registerConnectStatusListener(address, mConnectStatusListener);

            connectDeviceIfNeeded();
        }
    }


    private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            BluetoothLog.v(String.format("DeviceDetailActivity onConnectStatusChanged %d in %s",
                    status, Thread.currentThread().getName()));

            mConnected = (status == STATUS_CONNECTED);
            if (!mConnected) {

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
               // ToastUtils.showShortMsg(String.format("%s", ByteUtils.byteToString(value)));
                Logger.i("data", String.format("%s", ByteUtils.byteToString(value)));
//                if(value !=null && value.length>3){
//                    String hex= value[3]+"";
//                    //Integer x=Integer.parseInt(hex,16);
//                    if(hex.equals("1")){
//                        String wifiConnect= value[11]+"";
//                        if(wifiConnect.equals("1")){
//                            //连接成功
//                            changeActivity(AddMattressInfoActivity.class);
//                            finish();
//
//                        }else {
//                            ToastUtils.showShortMsg("WIFI连接失败");
//                            prompt1.setText("连接失败,请重新输入Wi-Fi密码");
//                            prompt1.setTextColor(getResources().getColor(R.color.progressend));
//                            prompt2.setText("请确认Wi-Fi密码正确且信号良好");
//                        }
//                    }
//
//
//
//                }





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

    @OnClick(R.id.btn_login)
    public void goHead() {
//        changeActivity(MattressConnectPowerActivity.class);
        String n = toUnicode(wifiName.getText().toString().trim());
        if (n.length() > 60) {
            ToastUtils.showShortMsg("WIFI名称太长，请修改");
            return;
        } else if (n.length() < 30) {
            nn = n;
            int a = 30 - n.length();
            for (int i = 0; i < a; i++) {
                nn += "0";
            }
            nb = "000000000000000000000000000000";
        } else {
            nn = n.substring(0, 30);
            nb = n.substring(30);
            if (nb.length() < 30) {
                int a = 30 - nb.length();
                for (int i = 0; i < a; i++) {
                    nb += "0";
                }
            }
        }
        String name = "AA14C101" + nn + "0A";

        byte[] command = BleCommondUtils.stringToBytes(name);
        ClientManager.getClient().write(address, mService, mWriteCharacter,
                command, mWriteRsp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String pass = "AA14C102" + nb + "0A";

                byte[] command = BleCommondUtils.stringToBytes(pass);
                ClientManager.getClient().write(address, mService, mWriteCharacter,
                        command, mWriteRsp);
            }
        }, 1000);

        String p = toUnicode(et_pass.getText().toString().trim());
        if (p.length() > 60) {
            ToastUtils.showShortMsg("WIFI密码太长，请修改");
            return;

        } else if (p.length() < 30) {
            pp = p;
            int a = 30 - p.length();
            for (int i = 0; i < a; i++) {
                pp += "0";
            }
            pb = "000000000000000000000000000000";
        } else {
            pp = p.substring(0, 30);
            pb = p.substring(30);
            if (pb.length() < 30) {
                int a = 30 - pb.length();
                for (int i = 0; i < a; i++) {
                    pb += "0";
                }
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String pass = "AA14C201" + pp + "0A";

                byte[] command = BleCommondUtils.stringToBytes(pass);
                ClientManager.getClient().write(address, mService, mWriteCharacter,
                        command, mWriteRsp);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String pass = "AA14C202" + pb + "0A";

                byte[] command = BleCommondUtils.stringToBytes(pass);
                ClientManager.getClient().write(address, mService, mWriteCharacter,
                        command, mWriteRsp);
            }
        }, 3500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String time = TimeUtil.convertTime("yyyyMMddHHmmss", System.currentTimeMillis()).substring(2);
                byte[] command = BleCommondUtils.stringToBytes("AA0AC3" + time + "0A");
                ClientManager.getClient().write(address, mService, mWriteCharacter,
                        command, mWriteRsp);
            }
        }, 4500);

    }

    private final BleWriteResponse mWriteRsp = new BleWriteResponse() {
        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                ToastUtils.showShortMsg("发送成功");
                changeActivity(AddMattressInfoActivity.class);
                finish();
            } else {
                ToastUtils.showShortMsg("发送失败");
            }
        }
    };

    public static String toUnicode(String s) {
        String as[] = new String[s.length()];
        String s1 = "";
        for (int i = 0; i < s.length(); i++) {
            as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
            s1 = s1 + as[i];
        }
        return s1.toUpperCase();
    }





    @OnClick(R.id.back)
    void back() {
        finish();
    }


    @Override
    protected void onDestroy() {
        ClientManager.getClient().disconnect(address);
        ClientManager.getClient().unregisterConnectStatusListener(address, mConnectStatusListener);
        super.onDestroy();
    }
}
