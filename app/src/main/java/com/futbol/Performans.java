package com.futbol;

import java.io.Serializable;

/**
 * Created by Yusuf on 17.01.2016.
 */
public class Performans extends Oyuncu implements Serializable{
    private static final long serialVersionUID = 2L;
    private int asil_mevki;
    String mevki;
    private float puan;
    public Performans(){
        super();
    }
    public Performans(String ad,int numara,boolean secim,String mevki,int asil_mevki,float puan)
    {
        super(ad,numara,secim);
        this.mevki=mevki;
        this.asil_mevki=asil_mevki;
        this.puan=puan;
    }
    public Performans(String ad,int numara) {
        super(ad,numara);
    }
    public int getAsil_mevki() {
        return asil_mevki;
    }

    public void setAsil_mevki(int asil_mevki) {
        this.asil_mevki = asil_mevki;
    }

    public String getMevki() {
        return mevki;
    }

    public void setMevki(String mevki) {
        this.mevki = mevki;
    }

    public float getPuan() {
        return puan;
    }

    public void setPuan(float puan) {
        this.puan = puan;
    }
}
