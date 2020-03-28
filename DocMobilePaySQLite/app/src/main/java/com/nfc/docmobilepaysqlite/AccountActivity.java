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

public class AccountActivity extends AppCompatActivity {
   DocMobilePayDatabaseHelper db;
    EditText accountNumberInput, bankInput,idPersonInput;
    Button buttonAddData, buttonViewAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        db=new DocMobilePayDatabaseHelper(this);

        accountNumberInput=(EditText) findViewById(R.id.account_number_edittext);
        bankInput=(EditText) findViewById(R.id.bank_edittext);
        idPersonInput=(EditText) findViewById(R.id.idperson_edittext);
        buttonAddData=(Button) findViewById(R.id.addaccount_button);
        AddData();
        buttonViewAllData=(Button) findViewById(R.id.afficher_account_button);
        ViewAll();
    }

    public void AddData(){
        buttonAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){// insert the new raw data on click of the button
                        boolean isInserted=db.accountInsertData(accountNumberInput.getText().toString(),bankInput.getText().toString(),idPersonInput.getText().toString());
                        if(isInserted==true)
                            Toast.makeText(AccountActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AccountActivity.this,"Data Not inserted",Toast.LENGTH_LONG);

                    }
                }
        );
    }

    public  void ViewAll(){
        buttonViewAllData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Cursor data =db.accountGetAllData();
                        if(data.getCount()==0){
                            //show message
                            showMessage("Error", "Data not found !");
                            return;
                        }
                        StringBuffer buffer= new StringBuffer();
                        while(data.moveToNext()){
                            buffer.append("Id"+ data.getString(0)+"\n");
                            buffer.append("Account N°:"+ data.getString(1)+"\n");
                            buffer.append("Bank: "+ data.getString(2)+"\n");
                            buffer.append("IdPerson"+ data.getString(3)+"\n");
                        }
                        showMessage("Data Account",buffer.toString());
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
