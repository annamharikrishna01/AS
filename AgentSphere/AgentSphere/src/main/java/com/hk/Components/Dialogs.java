package com.hk.Components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.hk.agentsphere.NewContactActivity;
import com.hk.agentsphere.NewLeadActivity;
import com.hk.agentsphere.NewListingActivity;
import com.hk.agentsphere.R;

/**
 * Created by Hari on 11/21/13.
 */

public  class Dialogs extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog.Builder clicked = builder.setTitle("Add New")
                .setItems(R.array.newlist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       switch (which)
                       {
                           case  0:

                               Intent lead= new Intent(getActivity().getApplicationContext(),NewLeadActivity.class);
                               startActivityForResult(lead,2);
                               break;
                           case 1:
                               Intent contact = new Intent(getActivity().getApplicationContext(), NewContactActivity.class);
                               startActivity(contact);
                               break;
                           case 2:
                               Intent listing =new Intent(getActivity().getApplicationContext(),NewListingActivity.class);
                               startActivity(listing);
                               break;
                       }

                    }
                });

        return builder.create();
    }

}




