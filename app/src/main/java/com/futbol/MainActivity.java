package com.futbol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   // String[] oyuncular;
    Button btnKaydet;
    OyuncuAdapter veriAdaptoru;
    AlertDialog alertDialogStores;
     int[] id;
    ArrayList<Performans> oyuncular;
    DatabaseFutbol dbFutbol;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbFutbol=new DatabaseFutbol(this);
        try {
            dbFutbol.createDataBase();
            dbFutbol.openDataBase();
        } catch (IOException e) {

            e.printStackTrace();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        OyuncuGoster();

    }
    private void OyuncuGoster()
    {

//        dbFutbol = new DatabaseFutbol(getApplicationContext()); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
//        dbFutbol.openDataBase();
        String selectQuery = "SELECT oyuncu.numara,oyuncu.ad,performans.mevki,performans.puan FROM oyuncu,performans where oyuncu.numara=performans.oyuncu_numara and performans.asil_mevki=1";
        Cursor c = dbFutbol.getDatabase().rawQuery(selectQuery,null);
        //int i=0;
        // int n=c.getCount();
        // oyuncular=new String[n];
        //id=new int[n];
        oyuncular=new ArrayList<Performans>();

        if (c.moveToFirst()) {

            do {
                //  Oyuncu o=new Oyuncu();
                Performans o=new Performans();
                //   Toast.makeText(getApplicationContext(), "Henüz Kitap Eklenmemiş.\nYukarıdaki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
                // oyuncular[i]= c.getString(c.getColumnIndex("ad"));
                //id[i++]=c.getInt(c.getColumnIndex("id"));
                o.setNumara(c.getInt(c.getColumnIndex("numara")));
                o.setAd(c.getString(c.getColumnIndex("ad")));
                o.setMevki(c.getString(c.getColumnIndex("mevki")));
                o.setPuan(c.getFloat(c.getColumnIndex("puan")));
                oyuncular.add(o);
            } while (c.moveToNext());

        }



        veriAdaptoru=new OyuncuAdapter(this,R.layout.oyuncu_list_view,oyuncular);
        ListView listemiz=(ListView) findViewById(R.id.listView1);
        registerForContextMenu(listemiz);
        //  Toast.makeText(getApplicationContext(), oyuncular.get(position).getNumara(), Toast.LENGTH_SHORT).show();
        listemiz.setAdapter(veriAdaptoru);
        listemiz.setClickable(true);


        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), oyuncular.get(position).getNumara(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_oyuncu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(getApplicationContext(), oyuncular.get(info.position).getAd(), Toast.LENGTH_LONG).show();
        int numara=oyuncular.get(info.position).getNumara();
        switch (item.getItemId()) {
            case R.id.oyuncu_duzenle:
                return true;
            case R.id.oyuncu_mevki_ekle:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private ArrayList<Performans> SecilenOyuncular(ArrayList<Performans> oyuncuLst){
        ArrayList<Performans> secilenOyuncuListesi=new ArrayList<Performans>();
        for(int i=0;i<oyuncuLst.size();i++){
            Performans o=oyuncuLst.get(i);
            if(o.isSecim()==true) {
                secilenOyuncuListesi.add(o);
                //  Toast.makeText(getApplicationContext(), secilenOyuncuListesi.get(0).toString(), Toast.LENGTH_SHORT).show();
            }
        }
        return secilenOyuncuListesi;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_oyuncu_ekle){
            Intent i=new Intent(MainActivity.this,OyuncuEkle.class);
            startActivity(i);
        }
        if(id==R.id.action_oyuncu_sil){

            final AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Dikkat!");
            builder.setMessage("İşaretlenen oyuncular silinsin mi?");
            builder.setCancelable(false);
            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Performans> oyuncuLst = veriAdaptoru.getOyuncuList();
                    dbFutbol.OyuncuSil(SecilenOyuncular(oyuncuLst));
                    OyuncuGoster();

                }
            });
            builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog=builder.create();
            dialog.show();

        }

        if(id==R.id.takimlari_hazirla){
            ArrayList<Performans> oyuncuLst=veriAdaptoru.getOyuncuList();
            Bundle e=new Bundle();
            e.putSerializable("Oyuncu", SecilenOyuncular(oyuncuLst));
           // e.putStringArrayList("Oyuncu",secilenOyuncuListesi);
            Intent intent=new Intent();
            intent.putExtras(e);
            intent.setClass(getApplicationContext(),TakimlariHazirlaActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


}
