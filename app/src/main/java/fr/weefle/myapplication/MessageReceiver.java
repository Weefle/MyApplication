package fr.weefle.myapplication;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageReceiver extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check the intent action
        if (intent.getAction() == "android.provider.Telephony.SMS_RECEIVED") {
            // Log the message
            Log.i("SmsBroadcastReceiver","Intent Received: "+intent.getAction());
            // Get the SMS message.
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String strMessage = "";
            String format = bundle.getString("format");
            // Retrieve the SMS message received.
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                // Check the Android version.
                boolean isVersionM =
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (isVersionM) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    // Build the message to show.
                    strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                    strMessage += " :" + msgs[i].getMessageBody() + "\n";
                    strMessage += " =>";
                    for(int j : getIntegers(msgs[i].getMessageBody()) ) {
                        strMessage += j;
                    }
                    // Display the SMS message.
                    Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static int[] getIntegers(String str) {

        ArrayList<Integer> list = new ArrayList<Integer>();

        //découper la phrase en mots
        String[] splited = str.split(" ");

        //parcourir les mots
        for (String current : splited) {
            try {
                //tenter de convertir le mot en int
                int parsedInt = Integer.parseInt (current);
                //ajouter l Integer à la list
                list.add(parsedInt);                    //un "auto boxing", une instance de Integer est créée à partir d'un int
            } catch (NumberFormatException e) {
                //c est pas un int
            }
        }

        //construire le résultat
        int[] result = new int[list.size()];
        for (int i = 0 ; i < list.size() ; i++) {
            //parcourir la list de Integer créée
            result[i] = list.get(i);
        }
        return result;
    }

}
