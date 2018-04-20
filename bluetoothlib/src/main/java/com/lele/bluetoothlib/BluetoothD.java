package com.lele.bluetoothlib;

import java.io.Serializable;

/**
 * Created by lele on 2018/4/20.
 * 蓝牙设备类
 */

public class BluetoothD implements Serializable {
    /**
     * 蓝牙名称
     */
    private String name;
    /**
     * 蓝牙设备mac地址
     */
    private String mac;

    public BluetoothD(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public boolean equals(Object obj) {
        BluetoothD device = (BluetoothD) obj;
        if (this.getName().equals(device.getName()) && this.getMac().equals(device.getMac())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "BluetoothD{" +
                "name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
