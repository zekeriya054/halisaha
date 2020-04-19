package com.futbol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Yusuf on 20.01.2016.
 */
public class OyuncuAdapter extends ArrayAdapter<Performans>  {


    private ArrayList<Performans> oyuncuList;
    Context context;
    public OyuncuAdapter(Context context, int resource, ArrayList<Performans> oyuncu) {
        super(context, 0, oyuncu);
        this.context=context;
        this.oyuncuList = new ArrayList<Performans>();
        this.oyuncuList.addAll(oyuncu);

    }

    public ArrayList<Performans> getOyuncuList() {
        return oyuncuList;
    }

    public void setOyuncuList(ArrayList<Performans> oyuncuList) {
        this.oyuncuList = oyuncuList;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.oyuncu_list_view, parent, false);
        }
        Performans o=getItem(position);
        TextView txtAd=(TextView) convertView.findViewById(R.id.txtAd);
        TextView txtMevki=(TextView) convertView.findViewById(R.id.txtMevki);
        TextView txtPuan=(TextView) convertView.findViewById(R.id.txtPuan);
        CheckBox chk=(CheckBox) convertView.findViewById(R.id.checkBox);
        // Populate the data into the template view using the data object
        txtAd.setText(o.getAd());
        txtMevki.setText(o.getMevki());
        DecimalFormat f = new DecimalFormat("0.00");
        txtPuan.setText(f.format(o.getPuan()));
        if(o.isSecim()==true) chk.setChecked(true);
            else chk.setChecked(false);
       // txtPuan.setText(String.format(String.valueOf(o.getPuan()),"%.2f"));
        final Performans oync = oyuncuList.get(position);

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                oync.setSecim(cb.isChecked());
              //  Toast.makeText(context.getApplicationContext(), "Clicked on Checkbox: " + " is " + oync.getAd() + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
/*
        txtAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(context.getApplicationContext(), oync.getAd(), Toast.LENGTH_LONG).show();
            }
        });

*/
        // Return the completed view to render on screen
        return convertView;
    }
}
