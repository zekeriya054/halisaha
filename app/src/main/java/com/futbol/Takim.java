package com.futbol;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yusuf on 27.01.2016.
 */
public class Takim extends Performans implements Serializable {
    private int takimPuan;
    private String takimRenk;
    private ArrayList<Performans> oyuncu=new ArrayList<Performans>();
    public Takim() {
        super();
    }

    public ArrayList<Performans> getOyuncu() {
        return oyuncu;
    }

    public void setOyuncu(ArrayList<Performans> oyuncu) {
        this.oyuncu = oyuncu;
    }

    public int getTakimPuan() {
        return takimPuan;
    }

    public void setTakimPuan(int takimPuan) {
        this.takimPuan = takimPuan;
    }

    public String getTakimRenk() {
        return takimRenk;
    }

    public void setTakimRenk(String takimRenk) {
        this.takimRenk = takimRenk;
    }
}
