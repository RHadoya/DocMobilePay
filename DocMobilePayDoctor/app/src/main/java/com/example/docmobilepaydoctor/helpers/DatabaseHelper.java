package com.example.docmobilepaydoctor.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.docmobilepaydoctor.modele.Account;
import com.example.docmobilepaydoctor.modele.Consultation;
import com.example.docmobilepaydoctor.modele.Payment;
import com.example.docmobilepaydoctor.modele.Person;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
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


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE table " + TABLE_NAME_PERSON + " ("+COL_1_PERSON+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2_PERSON+" TEXT, "+COL_3_PERSON+" TEXT, "+COL_4_PERSON+" INTEGER)");
        db.execSQL("CREATE table " + TABLE_NAME_ACCOUNT + " ("+COL_1_ACCOUNT+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2_ACCOUNT+" INTEGER, "+COL_3_ACCOUNT+" INTEGER, "+COL_4_ACCOUNT+" INTEGER)");
        db.execSQL("CREATE table " + TABLE_NAME_CONSULTATION + " ("+COL_1_CONSULTATION+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+COL_2_CONSULTATION+" INTEGER, "+COL_3_CONSULTATION+" INTEGER, "+COL_4_CONSULTATION+" DATE, "+COL_5_CONSULTATION+" FLOAT)");
        db.execSQL("CREATE table " + TABLE_NAME_PAYMENT + " ("+COL_1_PAYMENT+" DATE PRIMARY KEY ,"+COL_2_PAYMENT+" INTEGER,  "+COL_3_PAYMENT+" FLOAT)");

        //PERSON
        db.execSQL("INSERT into " + TABLE_NAME_PERSON + " ("+COL_2_PERSON +","+ COL_3_PERSON +"," +COL_4_PERSON + ") values ('Hahnemann','Annick',1)");
        db.execSQL("INSERT into " + TABLE_NAME_PERSON + " ("+COL_2_PERSON +","+ COL_3_PERSON +"," +COL_4_PERSON + ") values ('Dubois','Jean',0)");
        db.execSQL("INSERT into " + TABLE_NAME_PERSON + " ("+COL_2_PERSON +","+ COL_3_PERSON +"," +COL_4_PERSON + ") values ('Swan','Elisa',0)");
        db.execSQL("INSERT into " + TABLE_NAME_PERSON + " ("+COL_2_PERSON +","+ COL_3_PERSON +"," +COL_4_PERSON + ") values ('Martin','Philip',0)");

        //ACCOUNT
        db.execSQL("INSERT into " + TABLE_NAME_ACCOUNT + " ("+COL_2_ACCOUNT +","+ COL_3_ACCOUNT +"," +COL_4_ACCOUNT + ") values (1,2000,1)");
        db.execSQL("INSERT into " + TABLE_NAME_ACCOUNT + " ("+COL_2_ACCOUNT +","+ COL_3_ACCOUNT +"," +COL_4_ACCOUNT + ") values (2,750,2)");
        db.execSQL("INSERT into " + TABLE_NAME_ACCOUNT + " ("+COL_2_ACCOUNT +","+ COL_3_ACCOUNT +"," +COL_4_ACCOUNT + ") values (3,200,3)");
        db.execSQL("INSERT into " + TABLE_NAME_ACCOUNT + " ("+COL_2_ACCOUNT +","+ COL_3_ACCOUNT +"," +COL_4_ACCOUNT + ") values (4,400,4)");
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
    public List<Account> getAllAccount(){
        Cursor data = personGetAllData();
        List<Account> res = new ArrayList<>();
        while(data.moveToNext()){
            Account a = new Account();
            a.setId(data.getInt(0));
            a.setAccountNumber(data.getInt(1));
            a.setBank(data.getInt(2));
            a.setIdPerson(data.getInt(3));
            res.add(a);
        }
        return res;
    }
    public Account getAccountById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from "+TABLE_NAME_ACCOUNT+" where id="+id,null);
        while(data.moveToNext()){
            Account a = new Account();
            a.setId(id);
            a.setAccountNumber(data.getInt(1));
            a.setBank(data.getInt(2));
            a.setIdPerson(data.getInt(3));
            return a;
        }
        return null;
    }

    public boolean updateAccount(String id, String newAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3_ACCOUNT,newAmount);
        return (db.update(TABLE_NAME_ACCOUNT,contentValues,"id="+id,null)==1)?false:true;
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
    public List<Consultation> getAllConsultation(){
        Cursor data = consultationGetAllData();
        List<Consultation> res = new ArrayList<>();
        while(data.moveToNext()){
            Consultation a = new Consultation();
            a.setId(data.getInt(0));
            a.setIdDoctor(data.getInt(1));
            a.setIdPatient(data.getInt(2));
            try {
                a.setDate((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse(data.getString(3)));
            }catch (Exception e){
                a.setDate(null);
            }
            a.setAmount(data.getInt(4));
            res.add(a);
        }
        return res;
    }

    public Consultation getConsultationById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from "+TABLE_NAME_CONSULTATION+" where id="+id,null);
        while(data.moveToNext()){
            Consultation c = new Consultation();
            c.setId(data.getInt(0));
            c.setIdDoctor(data.getInt(1));
            c.setIdPatient(data.getInt(2));
            try {
                c.setDate((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse(data.getString(3)));
            }catch (Exception e){
                c.setDate(null);
            }
            c.setAmount(data.getInt(4));
            return c;
        }
        return null;
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
    public List<Payment> getAllPayment(){
        Cursor data = paymentGetAllData();
        List<Payment> res = new ArrayList<>();
        while(data.moveToNext()){
            Payment a = new Payment();
            try {
                a.setTimeStamp((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse(data.getString(0)));
            }catch (Exception e){
                a.setTimeStamp(null);
            }
            a.setIdConsultation(data.getInt(1));
            a.setAmount(data.getInt(2));
            res.add(a);
        }
        return res;
    }
    public Payment getPaymentById(int idConstultation) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + TABLE_NAME_PAYMENT + " where id=" + idConstultation, null);
        while (data.moveToNext()) {
            Payment a = new Payment();
            try {
                a.setTimeStamp((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse(data.getString(0)));
            }catch (Exception e){
                a.setTimeStamp(null);
            }
            a.setIdConsultation(data.getInt(1));
            a.setAmount(data.getInt(2));
            return a;
        }
        return null;
    }
    public Payment getPaymentByIdConsultation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + TABLE_NAME_PAYMENT + " where idconsultation=" + id, null);
        while (data.moveToNext()) {
            Payment a = new Payment();
            try {
                a.setTimeStamp((java.sql.Date) new SimpleDateFormat("dd/MM/yyyy").parse(data.getString(0)));
            }catch (Exception e){
                a.setTimeStamp(null);
            }
            a.setIdConsultation(data.getInt(1));
            a.setAmount(data.getInt(2));
            return a;
        }
        return null;
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
    public Cursor personDataById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME_PERSON+" where id="+id,null);
        return  result;
    }
    public List<Person> getAllPerson(){
        Cursor data = personGetAllData();
        List<Person> res = new ArrayList<>();
        while(data.moveToNext()){
            Person p = new Person();
            p.setId(data.getInt(0));
            p.setName(data.getString(1));
            p.setFirstName(data.getString(2));
            p.setIsDoctor(data.getInt(3));
            res.add(p);
        }
        return res;
    }
    public List<Person> getAllPatient(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from "+TABLE_NAME_PERSON+" where isdoctor=0",null);
        List<Person> res = new ArrayList<>();
        while(data.moveToNext()){
            Person p = new Person();
            p.setId(data.getInt(0));
            p.setName(data.getString(1));
            p.setFirstName(data.getString(2));
            p.setIsDoctor(data.getInt(3));
            res.add(p);
        }
        return res;
    }
    public Person getPersonById(int id){
        Cursor data = personDataById(id);
        while(data.moveToNext()){
            Person p = new Person();
            p.setId(Integer.valueOf(data.getString(0)));
            p.setName(data.getString(1));
            p.setFirstName(data.getString(2));
            p.setIsDoctor(data.getInt(3));
            return p;
        }
        return null;
    }

    //Other functions
    public String getCurrentDate(){ // format: "yyyy-MM-dd hh:mm:ss"
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

}
