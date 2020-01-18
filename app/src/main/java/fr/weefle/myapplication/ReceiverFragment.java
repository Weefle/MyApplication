package fr.weefle.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import java.util.ArrayList;
import java.util.List;

public class ReceiverFragment extends Fragment {

    private static final Uri SMS_URI_INBOX = Uri.parse("content://sms/inbox");

    private static final Uri SMS_URI_ALL = Uri.parse("content://sms/");

    private static final Uri SMS_URI_OUTBOX = Uri.parse("content://sms/sent");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_receiver, container, false);

        final ListView list = (ListView) rootView.findViewById(android.R.id.list);
        final List<String> messages = retrieveMessages(getActivity().getContentResolver());

        if (messages != null)
        {
            list.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, messages));
        }

        return rootView;
    }

    private List<String> retrieveMessages(ContentResolver contentResolver)
    {
        final List<String> messages = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(getActivity(),new String[] { Manifest.permission.READ_SMS}, 1);
        }
        else {
// Permission has already been granted
            final Cursor cursor = contentResolver.query(ReceiverFragment.SMS_URI_INBOX, null, null, null, null);

            if (cursor == null)
            {
                Log.e("retrieveMessages", "Cannot retrieve the messages");
                return null;
            }

            if (cursor.moveToFirst() == true)
            {
                do
                {
                    final String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    final String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));

                    messages.add(address + " - " + body);

                    Log.d("retrieveContacts", "The message with from + '" + address + "' with the body '" + body + "' has been retrieved");
                }
                while (cursor.moveToNext() == true);
            }

            if (cursor.isClosed() == false)
            {
                cursor.close();
            }
        }


        return messages;
    }
}
