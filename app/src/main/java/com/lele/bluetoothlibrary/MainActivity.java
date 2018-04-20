package com.lele.bluetoothlibrary;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lele.bluetoothlib.BluetoothD;
import com.lele.bluetoothlib.BluetoothDeviceList;
import com.lele.bluetoothlib.BluetoothM;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    //    private boolean isRegFilter = false;
    private boolean isDiscovering = false;
    private BluetoothM bluetoothM;
    private RecyclerView recyclerView;
    private TextView tx_data;
    private Button bt_search;
    BluetoothListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initUI();
        bluetoothM = new BluetoothM(context);
        bluetoothM.setBluetoothListener(listener);
        if (!bluetoothM.isBluetoothlAvaiable()) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
        }
        bluetoothM.registerBroadcast();
//        isDiscovering = bluetoothAdapter.startDiscovery();
//        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//        if (pairedDevices.size() > 0) {
//            // Loop through paired devices
//            for (BluetoothDevice device : pairedDevices) {
//                // Add the name and address to an array adapter to show in a ListView
//                Log.e("leleTest", device.getName() + "\ns" + device.getAddress());
//            }
//        }

    }

    BluetoothM.bluetoothListener listener = new BluetoothM.bluetoothListener() {
        @Override
        public void foundBluetooth(BluetoothD d) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void bluetoothState(int state) {
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    Toast.makeText(context, "STATE_OFF 手机蓝牙关闭", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Toast.makeText(context, "STATE_TURNING_OFF 手机蓝牙正在关闭", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.STATE_ON:
                    Toast.makeText(context, "STATE_ON 手机蓝牙开启", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    Toast.makeText(context, "STATE_TURNING_ON 手机蓝牙正在开启", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }

        @Override
        public void pairState(BluetoothDevice device, int state) {
            switch (state) {
                case BluetoothDevice.BOND_NONE:
                    Toast.makeText(context, device.getName() + "BOND_NONE 删除配对", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothDevice.BOND_BONDING:
                    Toast.makeText(context, device.getName() + "BOND_BONDING 正在配对", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothDevice.BOND_BONDED:
                    Toast.makeText(context, device.getName() + "BOND_BONDED 配对成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onDeviceConnected(String mDeviceName, String mDeviceAddress) {
            Toast.makeText(context, "Connected to " + mDeviceName + "\n" + mDeviceAddress, Toast.LENGTH_SHORT).show();
        }
    };

    private void initUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tx_data = (TextView) findViewById(R.id.tx_data);
        bt_search = (Button) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(this);
        adapter = new BluetoothListAdapter(context, BluetoothDeviceList.getInstance().list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new BluetoothListAdapter.OnItemClick() {
            @Override
            public void onPairClick(View view, BluetoothD bluetoothD) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bluetoothM.isBluetoothEnable()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 100);
        } else {
            bluetoothM.startDiscoveryBluetooth();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothM.unRegisterBroadcast();
    }

    private void pair(BluetoothDevice device) {
//        BluetoothDevice mBluetoothDevice = bluetoothAdapter.getRemoteDevice(device.getAddress());
//        BluetoothSocket socket;
//        try {
//            socket = mBluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(String.valueOf(device.getUuids())));
//            if (mBluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
//                Method creMethod = BluetoothDevice.class.getMethod("createBond");
//                creMethod.invoke(mBluetoothDevice);
//            } else {
//                Toast.makeText(this, "已经配对", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, "无法进行配对..." + e.toString(), Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("leleTest", "requestCode=" + requestCode + "resultCode=" + resultCode);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                bluetoothM.startDiscoveryBluetooth();
                Toast.makeText(this, "Open success", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Open fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_search) {
            bluetoothM.startDiscoveryBluetooth();
        }
    }
}
