package com.hk.Components;

import android.app.ActionBar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hari on 12/5/13.
 */
public class DynamicControls {

    public static HashMap<String,String> AddEditText(final View view, final int Section, final int NextTo, final String Value, final String Type, final String Hint)
    {
       HashMap<String,String> addedControls = new HashMap<String, String>();

        final LinearLayout ll = (LinearLayout)view.findViewById(Section);
        if(Type == "Email")
        {
            EditText text = new EditText(view.getContext());
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            text.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            text.setHint(Hint);
            text.setTextSize(16);
            text.addTextChangedListener(new TextWatcher() {
                boolean isCreated =false;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(count == 0)
                    {
                        if(start == 0)
                        {
                          ll.removeViewAt(ll.getChildCount()-1);
                        }

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(s != null && !isCreated)
                    {
                        DynamicControls.AddEditText(view,Section,NextTo,Value,Type,Hint);
                        isCreated =true;
                    }

                }
            });
            ll.addView(text);
        }else if(Type == "Number")
        {

            EditText text = new EditText(view.getContext());
            text.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setInputType(InputType.TYPE_CLASS_PHONE);
            ll.addView(text);
        }else if(Type == "Text"){

            EditText text = new EditText(view.getContext());
            text.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setInputType(InputType.TYPE_CLASS_TEXT);
            ll.addView(text);
        }
       return addedControls;
    }
    public static  HashMap<String,String> AddSpinner(View view,int Section,int NextTo,String selected,ArrayList<String> Values)
    {
        HashMap<String,String> addedControls = new HashMap<String, String>();
        LinearLayout ll = (LinearLayout)view.findViewById(Section);


        return addedControls;
    }
}
