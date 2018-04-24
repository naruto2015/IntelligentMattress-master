package com.rt.sm.activity.mattress;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.rt.sm.R;
import com.rt.sm.activity.MainActivity;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.common.Constants;
import com.rt.sm.utils.ClientManager;
import com.rt.sm.utils.SharedPreferenceHelper;
import com.rt.sm.utils.ToastUtils;
import com.rt.sm.view.CircleWaveView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchMattresActivity extends BaseActivity {


    @BindView(R.id.circle)
    CircleWaveView circle;


    @BindView(R.id.btn_start_search)
    Button btn_start_search;

    private BluetoothAdapter mBluetoothAdapter;

    private boolean isSearch;

    @Override
    protected int bindLayout() {
        return R.layout.activity_search_mattres;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        circle.start();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 判断设备是否支持蓝牙功能
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.showShortMsg("不支持BLE设备");
            finish();
        }
        // 判断系统是否支持蓝牙
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            ToastUtils.showShortMsg("不支持蓝牙设备");
            finish();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 3);
        }

        // 判断设备是否打开蓝牙协议
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, Constants.BLE_REQUEST);
            }
        }
        searchDevice();
        circle.start();



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void searchDevice() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(5000, 2).build();

        ClientManager.getClient().search(request, mSearchResponse);
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }


    @OnClick(R.id.btn_start_search)
    public void startSearch(){
        String content=btn_start_search.getText().toString();
        if(content.equals("取消搜索")){
            ClientManager.getClient().stopSearch();
            circle.stop();
            btn_start_search.setText("开始搜索");
        }else {
            circle.start();
            btn_start_search.setText("取消搜索");
            searchDevice();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.BLE_REQUEST && resultCode == Activity.RESULT_CANCELED) {
            ToastUtils.showShortMsg("定位权限必须打开");
        } else {
            searchDevice();
        }
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            BluetoothLog.w("MainActivity.onSearchStarted");
        }

        @Override
        public void onDeviceFounded(SearchResult device) {
//            BluetoothLog.w("MainActivity.onDeviceFounded " + device.device.getAddress());
            //ToastUtils.showShortMsg(device.getName() + "  " + device.getAddress());
            if (device.getName().contains("SLEEP_") && !isSearch) {
                isSearch = true;
                SharedPreferenceHelper.setAddress(device.getAddress());
                changeActivity(AddMatressWifiActivity.class);
                finish();
            }
        }

        @Override
        public void onSearchStopped() {
            BluetoothLog.w("MainActivity.onSearchStopped");
            if (!isSearch) {
                searchDevice();
            }
        }

        @Override
        public void onSearchCanceled() {
            BluetoothLog.w("MainActivity.onSearchCanceled");
        }
    };



}
