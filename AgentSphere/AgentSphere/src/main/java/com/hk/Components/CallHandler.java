package com.hk.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Hari on 11/21/13.
 */
public class CallHandler extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = getResultData();
        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Toast toast = Toast.makeText(context,phoneNumber,Toast.LENGTH_LONG);
        }
        setResultData(null);

    }
}
