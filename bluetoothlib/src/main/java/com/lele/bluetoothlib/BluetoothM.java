package com.lele.bluetoothlib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.UUID;

/**
 * Created by lele on 2018/4/20.
 * 蓝牙管理类
 */

public class BluetoothM {
    private Context context;
    private BluetoothAdapter mbluetoothAdapter;
    private boolean isRegFilter = false;
    public static final UUID MY_UUID=UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    public static final String NAME = "Bluetooth Secure";

    public BluetoothM(Context context) {
        this.context = context;
        mbluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    /**
     * 手机是否支持蓝牙
     *
     * @return
     */
    public boolean isBluetoothlAvaiable() {
        return mbluetoothAdapter != null;
    }

    /**
     * 蓝牙是否开启
     *
     * @return
     */
    public boolean isBluetoothEnable() {
        return mbluetoothAdapter.isEnabled();
    }

    /**
     * 开启蓝牙
     * 第二种开启方法：
     * Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
     * startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
     * REQUEST_ENABLE_BT常量必须大于0,系统在你的onActivityResult()实现中返回给你作为requestCode参数
     * resultCode==RESULT_OK：开启蓝牙程 resultCode==RESULT_CANCELED没有开启
     */
    public void bluetoothEnable() {
        mbluetoothAdapter.enable();
    }

    /**
     * 开始搜索蓝牙设备
     * 注册广播BluetoothDevice.ACTION_FOUND可以
     */
    public void startDiscoveryBluetooth() {
        mbluetoothAdapter.startDiscovery();

    }

    public void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        context.registerReceiver(br, filter);
        isRegFilter = true;
    }

    public void unRegisterBroadcast() {
        if (isRegFilter) {
            context.unregisterReceiver(br);
            isRegFilter = false;
        }
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!TextUtils.isEmpty(device.getName()) && !device.getName().equals("null") && !device.getName().equals("NULL")) {
                    BluetoothD d = new BluetoothD(device.getName(), device.getAddress());
                    BluetoothDeviceList.getInstance().insert(d);
                    if (listener != null) {
                        listener.foundBluetooth(d);
                    }
                    Log.e("leleTest", "bluetooth list" + BluetoothDeviceList.getInstance().list);
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                if (listener != null) {
                    listener.bluetoothState(state);
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                if (listener != null) {
                    listener.pairState(device, state);
                }
            }
        }
    };

    private bluetoothListener listener;

    public void setBluetoothListener(bluetoothListener listener) {
        this.listener = listener;
    }

    public interface bluetoothListener {
        /**
         * 发现蓝牙设备
         *
         * @param d
         */
        void foundBluetooth(BluetoothD d);

        /**
         * 蓝牙状态
         *
         * @param state
         */
        void bluetoothState(int state);

        /**
         * @param device
         * @param state
         */
        void pairState(BluetoothDevice device, int state);

        void onDeviceConnected(String mDeviceName, String mDeviceAddress);
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BluetoothState.Connect.MESSAGE_DEVICE_NAME:
                    String mDeviceName = msg.getData().getString(BluetoothState.BluetoothDevice.DEVICE_NAME);
                    String mDeviceAddress = msg.getData().getString(BluetoothState.BluetoothDevice.DEVICE_ADDRESS);
                    if (listener != null) {
                        listener.onDeviceConnected(mDeviceName, mDeviceAddress);
                    }
                    break;
                default:
                    break;
            }
        }
    };


}
