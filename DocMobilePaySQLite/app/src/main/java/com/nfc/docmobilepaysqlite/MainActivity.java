package com.nfc.docmobilepaysqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nfc.docmobilepaysqlite.helpers.DocMobilePayDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creation of the database with the tables
        DocMobilePayDatabaseHelper db=new DocMobilePayDatabaseHelper(this);

        Button buttonAccount = findViewById(R.id.buttonOpenAccount);
        Button buttonPerson = findViewById(R.id.buttonOpenPerson);
        Button buttonConsultation = findViewById(R.id.buttonOpenConsultation);
        Button buttonPayment = findViewById(R.id.buttonOpenPayment);

        buttonAccount.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent accountActivityIntent = new Intent(getApplicationContext(), AccountActivity.class);
                        startActivity(accountActivityIntent);
                    }
                }
        );
        buttonPerson.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent personActivityIntent = new Intent(getApplicationContext(), PersonActivity.class);
                        startActivity(personActivityIntent);
                    }
                }
        );
        buttonConsultation.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent consultationActivityIntent = new Intent(getApplicationContext(), ConsultationActivity.class);
                        startActivity(consultationActivityIntent);
                    }
                }
        );
        buttonPayment.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent paymentActivityIntent = new Intent(getApplicationContext(), PaymentActivity.class);
                        startActivity(paymentActivityIntent);
                    }
                }
        );

    }
}
