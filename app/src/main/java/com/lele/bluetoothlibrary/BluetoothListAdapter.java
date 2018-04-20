package com.lele.bluetoothlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lele.bluetoothlib.BluetoothD;

import java.util.List;

/**
 * Created by lele on 2018/4/20.
 */

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapter.Viewholer> {
    private Context context;
    private List<BluetoothD> list;

    public BluetoothListAdapter(Context context, List<BluetoothD> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Viewholer onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.list_bluetooth_device, null);
        Viewholer viewholer = new Viewholer(view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(final Viewholer holder, int position) {
        final BluetoothD bluetoothD = list.get(position);
        holder.tx_name.setText(bluetoothD.getName());
        holder.tx_mac.setText(bluetoothD.getMac());
        holder.tx_pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onPairClick(holder.itemView,bluetoothD);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholer extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView tx_name, tx_mac, tx_pair;

        public Viewholer(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tx_name = (TextView) itemView.findViewById(R.id.tx_name);
            tx_mac = (TextView) itemView.findViewById(R.id.tx_mac);
            tx_pair = (TextView) itemView.findViewById(R.id.tx_pair);
        }
    }

    private OnItemClick listener;

    public void setOnItemClick(OnItemClick listener) {
        this.listener = listener;
    }

    public interface OnItemClick {
        void onPairClick(View view, BluetoothD bluetoothD);
    }

}
