package fr.weefle.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class PairedListAdapter extends BaseAdapter {

    private Context context;
    private Set<BluetoothDevice> pairedDevices;
    private LayoutInflater inflater;
    private BluetoothAdapter myBluetooth;

    public PairedListAdapter(Context context, ArrayList<BluetoothDevice> devices){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.pairedDevices = pairedDevices;
    }

    @Override
    public int getCount() {
        return pairedDevices.size();
    }

    @Override
    public Object getItem(int position) {
        Iterator<BluetoothDevice> it = pairedDevices.iterator();
        while(it.hasNext()){
            BluetoothDevice d = it.next();
            return d;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_paired,null);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = myBluetooth.getBondedDevices();

        Iterator<BluetoothDevice> it = pairedDevices.iterator();
        while(it.hasNext()){
            BluetoothDevice d = it.next();
            String devicename = d.getName();
            TextView name = convertView.findViewById(R.id.paired_name);
            name.setText(devicename);
            String deviceadress = d.getAddress();
            TextView address = convertView.findViewById(R.id.paired_address);
            address.setText(deviceadress);
        }

        /*convertView.setOnClickListener(v -> {
            BluetoothActivity.mBluetoothAdapter.cancelDiscovery();

            //String device_name = BluetoothActivity.mBTDevices.get(position).getName();
            //String device_address = BluetoothActivity.mBTDevices.get(position).getAddress();

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
                BluetoothActivity.mBTDevices.get(position).createBond();
            }
        });*/

        return convertView;
    }

}
