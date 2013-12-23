package com.hk.outgoing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by Hari on 12/19/13.
 */
public class OutgoingListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CallStateListener(context,"Out"), PhoneStateListener.LISTEN_CALL_STATE);
      //  String phoneNumber = getResultData();
      //  if (phoneNumber == null) {
       //     phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
       //     Log.v("Phone Number",phoneNumber);
       // }
    }
}
