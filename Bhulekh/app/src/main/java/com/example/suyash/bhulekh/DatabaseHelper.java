package com.example.suyash.bhulekh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BOARD_DB.db";
    public static final String TABLE_NAME = "KHATA_TABLE";
    public static final String K_NUM = "khata_number";
    public static final String K_PART = "part";
    public static final String K_FASLI_YEAR = "fasli_year";
    public static final String K_LAND_TYPE = "land_type";


    public static final String G_TABLE_NAME = "Gata_table";
    public static final String K_NUM1 = "khata_number";
    public static final String G_KHASRANO = "khasra_number";
    public static final String G_SEQUENCENO = "seq_no";
    public static final String G_AREA = "area";
    public static final String G_YR_CO_TEN = "yr_co_ten";



    public static final String O_TABLE_NAME = "Owner_table";
    public static final String K_NUM2 = "khata_number";
    public static final String O_OWNER = "name";
    public static final String O_FATHER = "father";
    public static final String O_ADDRESS = "address";
    public static final String O_OWNER_NUMBER = "owner_no";

     /*
    public static final int O1_VERSION1 = 1;
    public static final String Or_TABLE_NAME = "Order_table";
    public static final String O1_ORDER_DESC = "order_desc";
    public static final String O1_SEQNO = "seq_no";
    public static final String O1_DEVICE_SERIALNO = "device_serialno";
    public static final String O1_DSC_DATE = "dsc_date";
    public static final String K_NUM3 = "khata_number";

    public static final int R_VERSION1 = 1;
    public static final String R_TABLE_NAME = "Remark_table";
    public static final String R_SEQUENCENO = "seq_no";
    public static final String R_REMARK_DESC = "remark_desc";
    public static final String K_NUM4 = "khata_number";

    public static final int L_VERSION1 = 1;
    public static final String L_TABLE_NAME = "Land_type";
    public static final String L_TYPE = "land_type";
    public static final String L_DESC = "land_type_desc";
    public static final String K_NUM5 = "khata_number";
    */
    //When this constructor is called Database will be created.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table "+TABLE_NAME+" ("+K_NUM+" text not null, "+K_PART+" text not null, "+K_FASLI_YEAR+" text not null, "+K_LAND_TYPE+" text not null)");
        db.execSQL("create table "+G_TABLE_NAME+" ("+K_NUM1+" text not null, "+G_KHASRANO+" text not null, "+G_SEQUENCENO+" text not null, "+G_AREA+" text not null, "+G_YR_CO_TEN+" text not null)");
        db.execSQL("create table " +O_TABLE_NAME+" ("+K_NUM2+" text not null, "+O_OWNER+" text not null, "+O_FATHER+" text not null, "+O_ADDRESS+" text not null, "+O_OWNER_NUMBER+" text not null)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + G_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + O_TABLE_NAME);
        onCreate(db);

    }

    public void insertData_Khata_Table(String khata_number, String part, String fasli_year, String land_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(K_NUM, khata_number);
        contentValues.put(K_PART, part);
        contentValues.put(K_FASLI_YEAR, fasli_year);
        contentValues.put(K_LAND_TYPE, land_type);
        db.insert(TABLE_NAME, null, contentValues);
    }
    public Cursor getData_Khata_Table(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+";", null);
        return cursor;
    }

    public void insertData_Gata_Table(String khata_number, String khasra_number, String seq_no, String area, String yr_co_ten){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(K_NUM1, khata_number);
        contentValues.put(G_KHASRANO, khasra_number);
        contentValues.put(G_SEQUENCENO, seq_no);
        contentValues.put(G_AREA, area);
        contentValues.put(G_YR_CO_TEN, yr_co_ten);
        db.insert(G_TABLE_NAME, null, contentValues);
    }
    public Cursor getData_Gata_Table(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+G_TABLE_NAME+";", null);
        return cursor;
    }

    public void insertData_Owner_Table(String khata_number, String name, String father, String address, String owner_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(K_NUM2, khata_number);
        contentValues.put(O_OWNER, name);
        contentValues.put(O_FATHER, father);
        contentValues.put(O_ADDRESS, address);
        contentValues.put(O_OWNER_NUMBER, owner_no);
        db.insert(O_TABLE_NAME, null, contentValues);
    }
    public Cursor getData_Owner_Table(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+O_TABLE_NAME+";", null);
        return cursor;
    }




}
