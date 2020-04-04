package com.example.docmobilepaydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docmobilepaydoctor.R;
import com.example.docmobilepaydoctor.helpers.DatabaseHelper;
import com.example.docmobilepaydoctor.modele.Person;

public class ConsultationAjoutActivity extends AppCompatActivity {
    private Button ajouterBtn;
    private Button annulerBtn;
    private EditText patientIdEdit;
    private EditText prixEdit;
    private EditText nomPatientEdit;
    private DatabaseHelper db;
    private Person p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_ajout);
        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ajouterBtn = findViewById(R.id.ajouter_cons_btn);
        annulerBtn = findViewById(R.id.annuler_cons_btn);
        patientIdEdit = findViewById(R.id.patient_cons_edit);
        prixEdit = findViewById(R.id.prix_cons_edit);
        nomPatientEdit = findViewById(R.id.nom_cons_edit);

        ajouterBtn.setOnClickListener(ajouterListener);
        annulerBtn.setOnClickListener(annulerListener);
        patientIdEdit.addTextChangedListener(idListener);
    }

    private View.OnClickListener annulerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener ajouterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(p != null) {
                db.consultationInsertData("1", patientIdEdit.getText().toString(), prixEdit.getText().toString());
                Toast.makeText(ConsultationAjoutActivity.this, "Ajouté", Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(ConsultationAjoutActivity.this, "Echec! ID patient introuvable", Toast.LENGTH_LONG).show();
            }
        }
    };

    private TextWatcher idListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            p = db.getPersonById(Integer.valueOf((!patientIdEdit.getText().toString().isEmpty())?patientIdEdit.getText().toString():"-1"));
            if(p != null && p.getId() != 1){
                nomPatientEdit.setText(p.getFirstName()+" "+p.getName());
            }else{
                nomPatientEdit.setText("INCONNU");
            }
        }
    };
}
