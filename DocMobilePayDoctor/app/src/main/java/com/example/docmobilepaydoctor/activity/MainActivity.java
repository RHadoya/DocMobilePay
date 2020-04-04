package com.example.docmobilepaydoctor.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.docmobilepaydoctor.R;
import com.example.docmobilepaydoctor.helpers.DatabaseHelper;
import com.example.docmobilepaydoctor.modele.Account;
import com.example.docmobilepaydoctor.modele.Person;

public class MainActivity extends AppCompatActivity {
    private Button consultationBtn;
    private Button patientBtn;
    private Button paiementBtn;
    private TextView docteurText;
    private TextView compteText;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        docteurText = findViewById(R.id.docteur_text);
        compteText = findViewById(R.id.compte_text);

        consultationBtn = findViewById(R.id.consultation_btn);
        patientBtn = findViewById(R.id.patient_btn);
        paiementBtn = findViewById(R.id.paiement_btn);

        consultationBtn.setOnClickListener(onClickListener);
        paiementBtn.setOnClickListener(onClickListener);
        patientBtn.setOnClickListener(onClickListener);

        init();
    }

    private void init(){
        if(db == null)
            db = new DatabaseHelper(this);
        Person docteur = db.getPersonById(1);
        Account compte = db.getAccountById(1);
        docteurText.setText(docteur.getFirstName()+" "+ docteur.getName());
        compteText.setText(compte.getBank()+" Euro");
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){
                case R.id.consultation_btn:
                    intent = new Intent(getBaseContext(), ConsultationActivity.class);
                    startActivity (intent);
                    return;
                case R.id.patient_btn:
                    intent = new Intent(getBaseContext(), PatientActivity.class);
                    startActivity (intent);
                    return;
                case R.id.paiement_btn:
                     intent = new Intent(getBaseContext(), PaiementActivity.class);
                    startActivity (intent);
                    return;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
