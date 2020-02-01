package fr.weefle.myapplication;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeviceListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();
    private LayoutInflater inflater;

    public DeviceListAdapter(Context context, ArrayList<BluetoothDevice> devices){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.devices = devices;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_bluetooth,null);

        BluetoothDevice device = (BluetoothDevice) getItem(position);
        String devicename = device.getName();
        TextView name = convertView.findViewById(R.id.bluetooth_name);
        name.setText(devicename);
        String deviceaddress = device.getAddress();
        TextView address = convertView.findViewById(R.id.bluetooth_address);
        address.setText(deviceaddress);

        convertView.setOnClickListener(v -> {
            BluetoothActivity.mBluetoothAdapter.cancelDiscovery();

            //String device_name = BluetoothActivity.mBTDevices.get(position).getName();
            //String device_address = BluetoothActivity.mBTDevices.get(position).getAddress();

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
                BluetoothActivity.mBTDevices.get(position).createBond();
            }
        });

        return convertView;
    }
}
