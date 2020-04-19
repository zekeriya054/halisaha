package com.futbol;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class TakimlariHazirlaActivity extends AppCompatActivity {
    private Bundle e = null;
    private ArrayList<Performans> oyuncu,kaleci,defans,ortaSaha,forvet,takimA,takimB;;
    private ArrayList<Performans>[] x=(ArrayList<Performans>[]) new ArrayList[6];
    private Random rnd=new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takimlari_hazirla);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        int m,n,k;
        float aPuan=0,bPuan=0;
        for(int i=0;i<x.length;i++) {
            x[i] = new ArrayList<Performans>();
        }
        oyuncu=(ArrayList<Performans>)getIntent().getSerializableExtra("Oyuncu");
        takimA=new ArrayList<Performans>();
        takimB=new ArrayList<Performans>();
        for(int i=0;i<oyuncu.size();i++) {
/*
            if(oyuncu.get(i).mevki=="K") x[0].add(oyuncu.get(i));
            if(oyuncu.get(i).mevki=="D") x[1].add(oyuncu.get(i));
            if(oyuncu.get(i).mevki=="O") x[2].add(oyuncu.get(i));
            if(oyuncu.get(i).mevki=="F") x[3].add(oyuncu.get(i));*/
            switch (oyuncu.get(i).mevki){
                case "K":x[0].add(oyuncu.get(i));break;//kaleci
                case "D":x[1].add(oyuncu.get(i));break;//defans
                case "O":x[2].add(oyuncu.get(i));break;//orta saha
                case "F":x[3].add(oyuncu.get(i));break;//forvet
            }
        }
        //kalecileri yerleştir
        m=rnd.nextInt(2);
        n=0;
        if(m==0) n=1;
     //   Log.i("Kaleci-1", ""+x[0].get(i).getAd());
      //  Log.i("Kaleci-2", ""+x[0].get(j).getAd());
        takimA.add(x[0].get(m));//kaleci A
        takimB.add(x[0].get(n));//kaleci B
        aPuan=aPuan+takimA.get(0).getPuan();
        bPuan=bPuan+takimB.get(0).getPuan();
        //defansı yerleştir
        m=0;n=1;
        Collections.sort(x[1], new Comparator<Performans>() {
            @Override
            public int compare(Performans oync1, Performans oync2) {
                Float puan1 = oync1.getPuan(), puan2 = oync2.getPuan();
                return puan2.compareTo(puan1);
            }
        });
        // iyi defansı düşük puanlı kalecinin olduğu takıma yaz
        if(aPuan>bPuan) {
            takimA.add(x[1].get(1));
            takimB.add(x[1].get(0));
        }
        else {
            takimA.add(x[1].get(0));
            takimB.add(x[1].get(1));
        }
        aPuan=aPuan+takimA.get(1).getPuan();
        bPuan=bPuan+takimB.get(1).getPuan();
        x[1].remove(0);
        x[1].remove(0);

        //diğer defans oyuncularını rasgele ata
        for(int i=0;x[1].size()>0;i++) {
            m = rnd.nextInt(x[1].size());
            if(i%2==0) {
                takimA.add(x[1].get(m));
                aPuan = aPuan + x[1].get(m).getPuan();
            }
            else {
                takimB.add(x[1].get(m));
                bPuan = bPuan + x[1].get(m).getPuan();
            }
              x[1].remove(m);
            //if(x[1].size()==0) break;
        }
        // orta sahayı yüksek puandan aşağı sırala
        Collections.sort(x[2], new Comparator<Performans>() {
            @Override
            public int compare(Performans oync1, Performans oync2) {
                Float puan1 = oync1.getPuan(), puan2 = oync2.getPuan();
                return puan2.compareTo(puan1);
            }
        });
        Log.i("size", "Defans " + aPuan + " " + bPuan);
       //düşük puanlı takıma bir tane iyi orta saha
        if(aPuan>bPuan) {
            takimB.add(x[2].get(0));
            takimA.add(x[2].get(1));
        }
        else {
            takimB.add(x[2].get(1));
            takimA.add(x[2].get(0));
        }
        aPuan=aPuan+takimA.get(0).getPuan();
        bPuan=bPuan+takimB.get(0).getPuan();

        x[2].remove(0);
        x[2].remove(0);
        // diğer orta saha oyuncularını ata
        for(int i=0;x[2].size()>0;i++) {
            m = rnd.nextInt(x[2].size());
            if(i%2==0) {
                takimA.add(x[2].get(m));
                aPuan = aPuan + x[2].get(m).getPuan();
            }
            else {
                takimB.add(x[2].get(m));
                bPuan = bPuan + x[2].get(m).getPuan();
            }
            x[2].remove(m);
            //if(x[2].size()==0) break;
        }
        Log.i("size", "Orta saha " + aPuan + " " + bPuan);
        // forveti yüksek puandan aşağı sırala
        Collections.sort(x[3], new Comparator<Performans>() {
            @Override
            public int compare(Performans oync1, Performans oync2) {
                Float puan1 = oync1.getPuan(), puan2 = oync2.getPuan();
                return puan2.compareTo(puan1);
            }
        });

        //düşük puanlı takıma bir tane iyi forvet
        if(aPuan>bPuan) {
            takimB.add(x[3].get(0));
            takimA.add(x[3].get(1));
        }
        else {
            takimB.add(x[3].get(1));
            takimA.add(x[3].get(0));
        }
        aPuan=aPuan+takimA.get(0).getPuan();
        bPuan=bPuan+takimB.get(0).getPuan();

        x[3].remove(0);
        x[3].remove(0);
        if(x[3].size()>0) {
            // diğer orta saha oyuncularını ata
            for (int i = 0;x[3].size()>0 ; i++) {
                m = rnd.nextInt(x[3].size());
                if (i % 2 == 0) {
                    takimA.add(x[3].get(m));
                    aPuan = aPuan + x[3].get(m).getPuan();
                } else {
                    takimB.add(x[3].get(m));
                    bPuan = bPuan + x[3].get(m).getPuan();
                }
                x[3].remove(m);
                //if (x[3].size() == 0) break;
            }
        }
        Log.i("size", "Forvet " + aPuan + " " + bPuan);
        //  Toast.makeText(getApplicationContext(), oyuncu2.get(0).toString(), Toast.LENGTH_SHORT).show();
        ListView l1=(ListView) findViewById(R.id.takim1);
        ListView l2=(ListView) findViewById(R.id.takim2);
        OyuncuAdapter adapter = new OyuncuAdapter(this,
                R.layout.takimlar_list_view, takimA);
        OyuncuAdapter adapter2 = new OyuncuAdapter(this,
                R.layout.takimlar_list_view, takimB);

       l1.setAdapter(adapter);
       l2.setAdapter(adapter2);
    }

}
