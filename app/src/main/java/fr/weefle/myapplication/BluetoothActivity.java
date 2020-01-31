package fr.weefle.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class BluetoothActivity extends AppCompatActivity {

    public static BluetoothAdapter mBluetoothAdapter = null;

    private int REQUEST_ENABLE=0;
    private int REQUEST_DISCOVERABLE=0;

    public static ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    private  DeviceListAdapter mDeviceListAdapter;
    private ListView lvNewDevices;
    private Switch aSwitchE;
    private Switch visib;
    private Button btanalys;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(!mBTDevices.contains(device)) {
                    mBTDevices.add(device);
                }
                mDeviceListAdapter = new DeviceListAdapter(context,mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);

            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        aSwitchE=findViewById(R.id.switch1);
        visib=findViewById(R.id.switch2);
        btanalys=findViewById(R.id.button);
        lvNewDevices=findViewById(R.id.listview);
        mBTDevices= new ArrayList<>();

        btanalys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.button) {


                        if (mBluetoothAdapter.isDiscovering()) {
                            mBluetoothAdapter.cancelDiscovery();
                            mBluetoothAdapter.startDiscovery();
                            IntentFilter intent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                            registerReceiver(mBroadcastReceiver,intent);
                        }else{
                            mBluetoothAdapter.startDiscovery();
                            IntentFilter intent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                            registerReceiver(mBroadcastReceiver,intent);
                        }

                        }



            }

        });

        visib.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            if (view.getId() == R.id.switch2) {


                                                if (visib.isChecked()) {

                                                    //if (!mBluetoothAdapter.isDiscovering()) {
                                                        Toast.makeText(getApplicationContext(), "Visibilité activé!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                                                        startActivityForResult(intent, REQUEST_DISCOVERABLE);
                                                    //}

                                                }
                                            }

                                        }

                                    });

        aSwitchE.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {

                                         if (view.getId() == R.id.switch1) {
                                             if (aSwitchE.isChecked()) {
                                                 Toast.makeText(getApplicationContext(), "Bluetooth activé!", Toast.LENGTH_SHORT).show();
                                                 Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                                 startActivityForResult(intent, REQUEST_ENABLE);
                                             } else {
                                                 mBluetoothAdapter.disable();
                                                 Toast.makeText(getApplicationContext(), "Bluetooth désactivé!", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
                                 });



    }




    }