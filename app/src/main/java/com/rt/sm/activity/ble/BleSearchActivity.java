package com.rt.sm.activity.ble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

import com.rt.sm.R;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.common.Constants;
import com.rt.sm.utils.Logger;
import com.rt.sm.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:Created by haohw on 2018/1/24.
 * 邮箱:303729942@qq.com
 * Blog:http://blog.csdn.net/qq_31660173
 */

public class BleSearchActivity extends BaseActivity {
    private List<BleBean> list = new ArrayList<>();
    private List<String> macList = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner scanner;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;
    private BleListAdapter bleListAdapter;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected int bindLayout() {
        return R.layout.activity_ble_search;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bleListAdapter = new BleListAdapter(list, this);
        listView.setAdapter(bleListAdapter);
        // 判断设备是否支持蓝牙功能
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.showShortMsg("不支持BLE设备");
            finish();
        }
        // 判断系统是否支持蓝牙
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        scanner = mBluetoothAdapter.getBluetoothLeScanner();
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            ToastUtils.showShortMsg("不支持蓝牙设备");
            finish();
            return;
        }
        mHandler = new Handler();
        scanLeDevice(true);
    }

    @Override
    protected void onResume() {
        // 判断设备是否打开蓝牙协议
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, Constants.BLE_REQUEST);
            }
        }
        super.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.BLE_REQUEST && resultCode == Activity.RESULT_CANCELED) {
            ToastUtils.showShortMsg("蓝牙开启失败");
        } else {
            scanner = mBluetoothAdapter.getBluetoothLeScanner();
            scanLeDevice(true);
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (Build.VERSION.SDK_INT < 21) {
            if (enable) {
                // 查询超时后自动停止
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    }
                }, SCAN_PERIOD);
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        } else {
            if (enable) {
                if (scanner == null) {
                    return;
                }
                scanner.startScan(scanCallback);
                mHandler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        scanner.stopScan(scanCallback);
                    }
                }, SCAN_PERIOD);
            } else {
                if (scanner != null) {
                    scanner.stopScan(scanCallback);
                } else {
                    return;
                }
            }
        }
    }


    // Device scan 回调(5.0以上）
    private ScanCallback scanCallback = new ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            showDevice(device);
        }


        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            ToastUtils.showShortMsg("filed: " + errorCode);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }
    };

    // Device scan 回调(5.0以下）
    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             final byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDevice(device);
                }
            });
        }
    };

    /**
     * 显示蓝牙列表
     *
     * @param device
     */
    private void showDevice(BluetoothDevice device) {
        Logger.d("ble", "ble device : --------" + device.getAddress());
        //筛选mac地址，先不加
//        if (device.getAddress().startsWith("00:A0:50")) {
//            if (Build.VERSION.SDK_INT < 21) {
//                mBluetoothAdapter.stopLeScan(mLeScanCallback);
//            } else {
//                scanner.stopScan(scanCallback);
//            }
//        }
        if (!macList.contains(device.getAddress())) {
            BleBean bleBean = new BleBean();
            bleBean.setMac(device.getAddress());
            bleBean.setName(device.getName());
            list.add(bleBean);
            macList.add(device.getAddress());
            bleListAdapter.notifyDataSetChanged();
        }
    }
}
