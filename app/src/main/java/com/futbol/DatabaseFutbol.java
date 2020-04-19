package com.futbol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Yusuf on 17.01.2016.
 */
public class DatabaseFutbol extends SQLiteOpenHelper{
    private static  final int VERSION=1;
    private static final String DB_PATH="/data/data/com.futbol/databases/";
    private static final String DB_NAME = "futbol";
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;
    public DatabaseFutbol(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
/*
        String sql="CREATE TABLE oyuncu(id INTEGER PRIMARY KEY AUTOINCREMENT,ad TEXT)";
        Log.d("DBHelper", "SQL : " + sql);
        db.execSQL(sql);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     /*   db.execSQL("DROP TABLE IF EXISTS oyuncu");
        onCreate(db);*/
    }
    /*
     * Eğer veritabanı yoksa kopyalayıp oluşturacak varsa hiçbir şey yapmayacak
     * metodumuz.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            /*
             * Veritabanı daha önce mevcut o yüzden herhangi bir işlem yapmaya
             * gerek yok.
             */
        } else {
            /*
             * Veritabanı daha önce hiç oluşturulmamış o yüzden veritabanını
             * kopyala
             */
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {

                throw new Error("Veritabanı kopyalanamadı");

            }
        }

    }
    /*
         * Veritabanı daha önce oluşturulmuş mu oluşturulmamış mı bunu öğrenmek için
         * yazılan method.
         */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    /*
     * Veritabanını assets'ten alıp
     * "/data/data/com.gelecegiyazanlar.orneksozluk/databases/" altına
     * kopyalayacak metod
     */
    private void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    /*
     * Uygulama içinde kullanacağımız db örneği
     * */
    public SQLiteDatabase getDatabase(){
        return sqLiteDatabase;
    }
    /*
         * Veritabanını uygulamada kullanacağımız zaman açmak için gerekli
         * metod
         */
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    /*
     * Veritabanını işimiz bittiğinde kapatmak için kullanacağımız metod
     */
    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }
    public void OyuncuKaydet(Performans o){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        ContentValues values2=new ContentValues();
        //values.put("id",o.getId());

        values.put("ad", o.getAd());
        values.put("numara",o.getNumara());
        db.insert("oyuncu",null,values);
 /*
        String selectQuery = "SELECT id,ad from oyuncu where ad='"+o.getAd()+"'";
        Cursor c = db.rawQuery(selectQuery,null);
        c.moveToFirst();
        int id=c.getInt(c.getColumnIndex("id"));
        Log.i("adı", ""+id);
        */
        values2.put("oyuncu_numara",o.getNumara());
        values2.put("mevki",o.getMevki());
        values2.put("asil_mevki",o.getAsil_mevki());
        values2.put("puan",o.getPuan());
        db.insert("performans",null,values2);
        db.close();
    }
    public void OyuncuSil(ArrayList<Performans> oyuncu){
        SQLiteDatabase db = this.getWritableDatabase();
        Performans o;
        for(int i=0;i<oyuncu.size();i++) {
             o=oyuncu.get(i);
            db.delete("performans", "oyuncu_numara=?", new String[]{String.valueOf(o.getNumara())});
        }
        db.close();
        }
    public Cursor OyuncuListele(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = this.getDatabase().rawQuery(sql,null);
        return c;
    }
 }



