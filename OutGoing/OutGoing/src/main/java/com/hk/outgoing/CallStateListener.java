package com.hk.outgoing;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Hari on 12/19/13.
 */
public class CallStateListener extends PhoneStateListener {

    public Context pcontext;
    public String type;

    public CallStateListener(Context context,String type)
    {
        super();
        this.pcontext = context;
        this.type = type;
    }
    @Override
    public void onCallStateChanged(int state,String number)
    {
        super.onCallStateChanged(state,number);
        switch (state)
        {
            case TelephonyManager.CALL_STATE_IDLE:
                Toast.makeText(pcontext,"Idle"+type +number,Toast.LENGTH_LONG).show();

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                 Toast.makeText(pcontext,"OffHook"+type +number,Toast.LENGTH_LONG).show();
                Intent i = new Intent(pcontext, showActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                i.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                i.putExtra("INCOMING_NUMBER", number);
                i.setAction(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                pcontext.startActivity(i);

                 break;
            case TelephonyManager.CALL_STATE_RINGING:
                 Toast.makeText(pcontext,"Ringing"+type +number,Toast.LENGTH_LONG).show();


                 break;

        }
    }


}
