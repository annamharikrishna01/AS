package com.hk.outgoing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Hari on 12/19/13.
 */
public class IncomingCallListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //telephonyManager.listen(new CallStateListener(context,"In"), PhoneStateListener.LISTEN_CALL_STATE);


        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d("IncomingBroadcastReceiver: onReceive: ", state);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)
                || state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

            Log.d("Ringing", "Phone is ringing");

            Intent i = new Intent(context, showActivity.class);
            i.putExtras(intent);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }
    }

}
