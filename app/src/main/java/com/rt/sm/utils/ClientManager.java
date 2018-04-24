package com.rt.sm.utils;

import com.inuker.bluetooth.library.BluetoothClient;
import com.rt.sm.common.MyApp;

/**
 * Created by dingjikerbo on 2016/8/27.
 */
public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(MyApp.APP_CONTEXT);
                }
            }
        }
        return mClient;
    }
}
