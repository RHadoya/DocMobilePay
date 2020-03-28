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

public class ConsultationActivity extends AppCompatActivity {
    DocMobilePayDatabaseHelper db;
    EditText idDoctorInput, idPatientInput,amountConsultationInput;
    Button buttonAddData, buttonViewAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        db=new DocMobilePayDatabaseHelper(this);

        idDoctorInput=(EditText) findViewById(R.id.iddoctor_edittext);
        idPatientInput=(EditText) findViewById(R.id.idpatient_edittext);
        amountConsultationInput=(EditText) findViewById(R.id.amountconsultation_edittext);
        buttonAddData=(Button) findViewById(R.id.addconsultation_button);
        AddData();
        buttonViewAllData=(Button) findViewById(R.id.afficher_consultation_button);
        ViewAll();
    }

    public void AddData(){
        buttonAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){// insert the new raw data on click of the button
                        boolean isInserted=db.consultationInsertData(idDoctorInput.getText().toString(),idPatientInput.getText().toString(),amountConsultationInput.getText().toString());
                        if(isInserted==true)
                            Toast.makeText(ConsultationActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ConsultationActivity.this,"Data Not inserted",Toast.LENGTH_LONG);

                    }
                }
        );
    }

    public  void ViewAll(){
        buttonViewAllData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Cursor data =db.consultationGetAllData();
                        if(data.getCount()==0){
                            //show message
                            showMessage("Error", "Data not found !");
                            return;
                        }
                        StringBuffer buffer= new StringBuffer();
                        while(data.moveToNext()){
                            buffer.append("Id: "+ data.getString(0)+"\n");
                            buffer.append("IdDoctor: "+ data.getString(1)+"\n");
                            buffer.append("IdPatient: "+ data.getString(2)+"\n");
                            buffer.append("DateTime: "+ data.getString(3)+"\n");
                            buffer.append("Amount Consult.: "+ data.getString(4)+"\n");
                        }
                        showMessage("Data Consultation",buffer.toString());
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
