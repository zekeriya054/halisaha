package com.futbol;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class OyuncuEkle extends AppCompatActivity {
    private static String[] mevki={"K","D","O","F"};
    Spinner sp;
    Button btnKaydet;
    DatabaseFutbol dbFutbol;
    private String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyuncu_ekle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, mevki);
        sp = (Spinner) findViewById(R.id.spnMevki);
        sp.setAdapter(arrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnKaydet=(Button) findViewById(R.id.btnKaydet);
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ad=(EditText) findViewById(R.id.txtAd);
                EditText numara=(EditText) findViewById(R.id.txtNumara);
                Integer n=Integer.parseInt(numara.getText().toString());
                EditText puan=(EditText) findViewById(R.id.txtPuan);
                Float p=Float.parseFloat(puan.getText().toString());
                Performans o=new Performans(ad.getText().toString(),n,false,m,1,p);
                dbFutbol=new DatabaseFutbol(OyuncuEkle.this);
                dbFutbol.OyuncuKaydet(o);

            }
        });
    }

}
