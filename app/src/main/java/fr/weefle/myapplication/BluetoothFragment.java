package fr.weefle.myapplication;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;


public class BluetoothFragment extends Fragment {


    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";




    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String info = ((TextView)view).getText().toString();
            String address = info.substring(info.length()-17);

            Intent intent = new Intent(getContext(), Test.class);
            intent.putExtra(EXTRA_ADDRESS,address);
            startActivity(intent);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth==null){
            Toast.makeText(getContext(),"Bluetooth device not available!",Toast.LENGTH_LONG).show();
        }else if(!myBluetooth.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }
        pairedDevices=myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if(pairedDevices.size()>0){
            for(BluetoothDevice device : pairedDevices){
                list.add(device.getName() + "\n" + device.getAddress());
            }
        }else{
            Toast.makeText(getContext(),"No paired Bluetooth device found!",Toast.LENGTH_LONG).show();

        }
        final View rootView = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        final ListView devicelist = (ListView) rootView.findViewById(R.id.listview1);
        final PairedListAdapter adapter = new PairedListAdapter(getContext(), list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);

        return rootView;
    }






}
