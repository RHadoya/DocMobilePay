package com.nfc.docmobilepaysqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nfc.docmobilepaysqlite.helpers.DocMobilePayDatabaseHelper;

public class PersonActivity extends AppCompatActivity {
    DocMobilePayDatabaseHelper db;
    EditText nameInput, firstnameInput,isDoctorInput;
    Button buttonAddData, buttonViewAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        db=new DocMobilePayDatabaseHelper(this);

        nameInput=(EditText) findViewById(R.id.name_edittext);
        firstnameInput=(EditText) findViewById(R.id.firstname_edittext);
        isDoctorInput=(EditText) findViewById(R.id.isdoctor_edittext);
        buttonAddData=(Button) findViewById(R.id.addperson_button);
        AddData();
        buttonViewAllData=(Button) findViewById(R.id.afficher_person_button);
        ViewAll();
    }

    public void AddData(){
        buttonAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){// insert the new raw data on click of the button
                        boolean isInserted=db.personInsertData(nameInput.getText().toString(),firstnameInput.getText().toString(),isDoctorInput.getText().toString());
                        if(isInserted==true)
                            Toast.makeText(PersonActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PersonActivity.this,"Data Not inserted",Toast.LENGTH_LONG);

                    }
                }
        );
    }

    public  void ViewAll(){
        buttonViewAllData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Cursor data =db.personGetAllData();
                        if(data.getCount()==0){
                            //show message
                            showMessage("Error", "Data not found !");
                            return;
                        }
                        StringBuffer buffer= new StringBuffer();
                        while(data.moveToNext()){
                            buffer.append("Id: "+ data.getString(0)+"\n");
                            buffer.append("Name: "+ data.getString(1)+"\n");
                            buffer.append("FirsName: "+ data.getString(2)+"\n");
                            buffer.append("IsDoctor: "+ data.getString(3)+"\n");
                        }
                        showMessage("Data Person",buffer.toString());
                    }
                }
        );
    }

    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
