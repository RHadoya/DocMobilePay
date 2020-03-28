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

public class PaymentActivity extends AppCompatActivity {

    DocMobilePayDatabaseHelper db;
    EditText idConsultationInput, amountPaidInput;
    Button buttonAddData, buttonViewAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        db=new DocMobilePayDatabaseHelper(this);

        idConsultationInput=(EditText) findViewById(R.id.idconsultation_edittext);
        amountPaidInput=(EditText) findViewById(R.id.amountpaid_edittext);

        buttonAddData=(Button) findViewById(R.id.addpayment_button);
        AddData();
        buttonViewAllData=(Button) findViewById(R.id.afficher_payment_button);
        ViewAll();
    }

    public void AddData(){
        buttonAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){// insert the new raw data on click of the button

                       boolean isInserted=db.paymentInsertData(idConsultationInput.getText().toString(),amountPaidInput.getText().toString());
                        if(isInserted==true)
                            Toast.makeText(PaymentActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PaymentActivity.this,"Data Not inserted",Toast.LENGTH_LONG);

                    }
                }
        );
    }


    public  void ViewAll(){
        buttonViewAllData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Cursor data =db.paymentGetAllData();
                        if(data.getCount()==0){
                            //show message
                            showMessage("Error", "Data not found !");
                            return;
                        }
                        StringBuffer buffer= new StringBuffer();
                        while(data.moveToNext()){
                            buffer.append("TimeStamp: "+ data.getString(0)+"\n");
                            buffer.append(" idConsultation: "+ data.getString(1)+"\n");
                            buffer.append("amountPaid: "+ data.getString(2)+"\n");

                        }
                        showMessage("Data Payment",buffer.toString());
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
