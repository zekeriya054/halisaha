package com.futbol;

import android.widget.Toast;

/**
 * Created by Yusuf on 18.02.2016.
 */

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/*
 * Here you can control what to do next when the user selects an item
 */
public class TiklananOyuncu implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Context context = view.getContext();

        TextView textViewItem = ((TextView) view.findViewById(R.id.txtAd));

        // get the clicked item name
        String listItemText = textViewItem.getText().toString();

        // get the clicked item ID
        String listItemId = textViewItem.getTag().toString();

        // just toast it
        Toast.makeText(context, "dsfsdfd", Toast.LENGTH_SHORT).show();

       // ((MainActivity) context).alertDialogStores.cancel();

    }
}