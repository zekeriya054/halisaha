package com.futbol;

import java.io.Serializable;

/**
 * Created by Yusuf on 17.01.2016.
 */
public class Oyuncu implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String ad;
    private int numara;
    private boolean secim=false;

    public Oyuncu(){
        super();
    }
    public Oyuncu(String ad,int numara){
        super();
        this.ad=ad;
        this.numara=numara;
    }
    public Oyuncu(String ad,int numara,boolean secim){
        super();
        this.ad=ad;
        this.numara=numara;
        this.secim=secim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumara() {
        return numara;
    }

    public void setNumara(int numara) {
        this.numara = numara;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public boolean isSecim() {
        return secim;
    }

    public void setSecim(boolean secim) {
        this.secim = secim;
    }
}
