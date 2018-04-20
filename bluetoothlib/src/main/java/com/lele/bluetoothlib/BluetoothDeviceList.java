package com.lele.bluetoothlib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lele on 2018/4/20.
 */

public class BluetoothDeviceList {
    public static BluetoothDeviceList mbluetoothDeviceList;
    public List<BluetoothD> list = new ArrayList<>();

    public BluetoothDeviceList() {

    }

    public static BluetoothDeviceList getInstance() {
        if (mbluetoothDeviceList == null) {
            mbluetoothDeviceList = new BluetoothDeviceList();
        }
        return mbluetoothDeviceList;
    }

    public void insert(BluetoothD device) {
        if (!list.contains(device)) {
            list.add(device);
        }
    }

    public void clear(){
        list.clear();
    }
}
