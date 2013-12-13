package com.hk.Components;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Hari on 11/21/13.
 */
public class DatePicker {

    private TextView displayDate;
    private DatePicker dpResult;
    public void getDateOnView(Context context,View v,int resourceid)
    {
        displayDate = (TextView)v.findViewById(resourceid);

    }

}
