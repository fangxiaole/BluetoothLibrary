package com.lele.bluetoothlib;

/**
 * Created by lele on 2018/4/20.
 */

public class BluetoothState {

    public static class BluetoothDevice {
        public static final String DEVICE_NAME = "device_name";
        public static final String DEVICE_ADDRESS = "device_address";
    }

    public static class Connect {
        public static final int MESSAGE_DEVICE_NAME = 1;
        public static final int STATE_CONNECTING = 2;
        public static final int STATE_CONNECTED = 3;
    }

}
