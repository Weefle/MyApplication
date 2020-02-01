package fr.weefle.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class PairedListAdapter extends BaseAdapter {

    private Context context;
    private Set<BluetoothDevice> pairedDevices;
    private LayoutInflater inflater;
    private BluetoothAdapter myBluetooth;
    public static String EXTRA_ADDRESS = "device_address";

    public PairedListAdapter(Context context, ArrayList<BluetoothDevice> devices){
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.pairedDevices = myBluetooth.getBondedDevices();

    }

    @Override
    public int getCount() {
        return pairedDevices.size();
    }

    @Override
    public Object getItem(int position) {
       return new ArrayList<BluetoothDevice>(pairedDevices).get(position);
        /*Iterator<BluetoothDevice> it = pairedDevices.iterator();
       // while(it.hasNext()){
            BluetoothDevice d = it.next();
            return d;*/
        //}

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_paired,null);


       /* Iterator<BluetoothDevice> it = pairedDevices.iterator();
        //while(it.hasNext()){
            BluetoothDevice d = it.next();*/
       BluetoothDevice d = new ArrayList<BluetoothDevice>(pairedDevices).get(position);
            String devicename = d.getName();
            TextView name = convertView.findViewById(R.id.paired_name);
            name.setText(devicename);
            String deviceaddress = d.getAddress();
            TextView address = convertView.findViewById(R.id.paired_address);
            address.setText(deviceaddress);
       // }
        convertView.setOnClickListener(v -> {
            String info = deviceaddress;
            String _address = info.substring(info.length()-17);

            Intent intent = new Intent(this.context, Control.class);
            intent.putExtra(EXTRA_ADDRESS,_address);
            Toast.makeText(this.context, _address, Toast.LENGTH_SHORT).show();
            this.context.startActivity(intent);

                });

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
