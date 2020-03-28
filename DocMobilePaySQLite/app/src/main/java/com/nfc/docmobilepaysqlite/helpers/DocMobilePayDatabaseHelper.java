package com.nfc.docmobilepaysqlite.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DocMobilePayDatabaseHelper extends SQLiteOpenHelper {
    public  static final String DATABASE_NAME = "DocMobilePay.db";

    // "Person" TABLE
    public  static final String TABLE_NAME_PERSON = "Person";
    public  static final String COL_1_PERSON = "ID";
    public  static final String COL_2_PERSON = "NAME";
    public  static final String COL_3_PERSON = "FIRSTNAME";
    public  static final String COL_4_PERSON = "ISDOCTOR";

    //"Account" TABLE
    static final String TABLE_NAME_ACCOUNT = "Account";
    public  static final String COL_1_ACCOUNT = "ID";
    public  static final String COL_2_ACCOUNT = "ACCOUNTNUMBER";
    public  static final String COL_3_ACCOUNT = "BANK";
    public  static final String COL_4_ACCOUNT = "IDPERSON";

    //"Consultation" TABLE
    public  static final String TABLE_NAME_CONSULTATION = "Consultation";
    public  static final String COL_1_CONSULTATION = "ID";
    public  static final String COL_2_CONSULTATION = "IDDOCTOR";
    public  static final String COL_3_CONSULTATION = "IDPATIENT";
    public  static final String COL_4_CONSULTATION = "DATETIME";
    public  static final String COL_5_CONSULTATION = "AMOUNTCONSULTATION";

    //"Payment" TABLE
    public  static final String TABLE_NAME_PAYMENT = "Payment";
    public  static final String COL_1_PAYMENT = "TIMESTAMP";
    public  static final String COL_2_PAYMENT = "IDCONSULTATION";
    public  static final String COL_3_PAYMENT = "AMOUNTPAID";


    public DocMobilePayDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE table " + TABLE_NAME_PERSON + " ("+COL_1_PERSON+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2_PERSON+" TEXT, "+COL_3_PERSON+" TEXT, "+COL_4_PERSON+" INTEGER)");
        db.execSQL("CREATE table " + TABLE_NAME_ACCOUNT + " ("+COL_1_ACCOUNT+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2_ACCOUNT+" INTEGER, "+COL_3_ACCOUNT+" INTEGER, "+COL_4_ACCOUNT+" INTEGER)");
        db.execSQL("CREATE table " + TABLE_NAME_CONSULTATION + " ("+COL_1_CONSULTATION+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2_CONSULTATION+" INTEGER, "+COL_3_CONSULTATION+" INTEGER, "+COL_4_CONSULTATION+" DATE, "+COL_5_CONSULTATION+" FLOAT)");
        db.execSQL("CREATE table " + TABLE_NAME_PAYMENT + " ("+COL_1_PAYMENT+" DATE PRIMARY KEY ,"+COL_2_PAYMENT+" INTEGER,  "+COL_3_PAYMENT+" FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME_CONSULTATION);
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME_PAYMENT);
        onCreate(db);
    }

    //CRUD
    //"Account" TABLE
    public boolean accountInsertData(String accountNumber, String bank, String idPerson){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_ACCOUNT,accountNumber);
        contentValues.put(COL_3_ACCOUNT,bank);
        contentValues.put(COL_4_ACCOUNT,idPerson);
        long result = db.insert(TABLE_NAME_ACCOUNT,null,contentValues);
        if(result ==1)
            return  false;
        else
            return true;

    }

    public Cursor accountGetAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME_ACCOUNT,null);
        return  result;
    }

    //"Consultation" TABLE
    public boolean consultationInsertData(String idDoctor, String idPatient, String amountConsultation){
        //Date: current date for the consultation
        String dateString=getCurrentDate();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_CONSULTATION,idDoctor);
        contentValues.put(COL_3_CONSULTATION,idPatient);
        contentValues.put(COL_4_CONSULTATION,dateString);
        contentValues.put(COL_5_CONSULTATION,amountConsultation);

        long result = db.insert(TABLE_NAME_CONSULTATION,null,contentValues);
        if(result ==1)
            return  false;
        else
            return true;

    }

    public Cursor consultationGetAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME_CONSULTATION,null);
        return  result;
    }

    //"Payment" TABLE
    public boolean paymentInsertData( String idConsultation, String amountPaid){
        //Date: current date for the payment()
        String dateString=getCurrentDate();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_PAYMENT,dateString);
        contentValues.put(COL_2_PAYMENT,idConsultation);
        contentValues.put(COL_3_PAYMENT,amountPaid);
        long result = db.insert(TABLE_NAME_PAYMENT,null,contentValues);
        if(result ==1)
            return  false;
        else
            return true;

    }

    public Cursor paymentGetAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME_PAYMENT,null);
        return  result;
    }

    //"Person" TABLE
    public boolean personInsertData(String name, String firstname, String isDoctor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_PERSON,name);
        contentValues.put(COL_3_PERSON,firstname);
        contentValues.put(COL_4_PERSON,isDoctor);
        long result = db.insert(TABLE_NAME_PERSON,null,contentValues);
        if(result ==1)
            return  false;
        else
            return true;

    }

    public Cursor personGetAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME_PERSON,null);
        return  result;
    }

    //Other functions
    public String getCurrentDate(){ // format: "yyyy-MM-dd hh:mm:ss"
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }



}
