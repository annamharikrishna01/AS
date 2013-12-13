package com.hk.Components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by Hari on 12/3/13.
 */
public class AutoComplete {

    private View view;
    private MultiAutoCompleteTextView Location;
    private ArrayList<String> loc;
    private Context context;
    private boolean IsRequiredQuery =false;
    private AlertDialog dialog;
    private ArrayAdapter<String> adapter;
    public void populate(View v,MultiAutoCompleteTextView  location,String Type)
    {

        context = v.getContext();
        Location = location;
        if(Type == "Locations")
        {
           adapter = new ArrayAdapter<String>
               (context, android.R.layout.simple_dropdown_item_1line,BindLocations());


        }else if(Type =="Features")
        {
            adapter = new ArrayAdapter<String>
                    (context, android.R.layout.simple_dropdown_item_1line,BindAmenities());

        }
        Location.setThreshold(2);
        Location.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        Location.setAdapter(adapter);
    }
    public ArrayList<String> BindLocations()
    {
        ArrayList<String> loct= new ArrayList<String>();
        loct.add("Hyderabad");
        loct.add("Bangolore");
        loct.add("Mumbai");
        loct.add("NewDelhi");
        return  loct;
    }

    public ArrayList<String> BindAmenities()
    {
        ArrayList<String> loct= new ArrayList<String>();
        loct.add("Swimming Pool");
        loct.add("Garage");
        loct.add("BasketBall Clourt");

        return  loct;
    }

    public ArrayList<String> FindItem(String cs,ArrayList<String> ls)
    {
        ArrayList<String> val= new ArrayList<String>();

        if(!cs.isEmpty())
        {
            String[] col = cs.split(",");
            for (String c : col)
            {
               boolean s = ls.contains(c);
                if(!s)
                {
                    val.add(c);
                }
            }

        }
        return  val;
    }

    public void ShowConfirm(ArrayList<String> list,Context context)
    {
        final ArrayList<String> temparrray = list;
        CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        final ArrayList seletedItems=new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("New Locations");


        builder.setMultiChoiceItems(cs,null,new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if (isChecked) {
                    seletedItems.add(which);

                } else if (seletedItems.contains(which)) {
                    seletedItems.remove(Integer.valueOf(which));
                }


            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String val = new String();
                for (Object s : seletedItems) {
                     val = temparrray.get(Integer.valueOf(s.toString()));
                     adapter.add(val);
                }

                adapter.notifyDataSetChanged();
                Location.showDropDown();
            }
        })
              .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {


                  }
              });

        dialog = builder.create();
        dialog.show();

    }

}
