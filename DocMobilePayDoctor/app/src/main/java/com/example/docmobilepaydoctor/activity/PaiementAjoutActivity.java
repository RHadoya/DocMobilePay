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
import com.example.docmobilepaydoctor.modele.Consultation;

public class PaiementAjoutActivity extends AppCompatActivity {
    private Button annulerBtn;
    private Button validerBtn;
    private Button payNfcBtn;
    private EditText consultationIdEdit;
    private EditText prixPaieEdit;
    private EditText etatPaieEdit;
    private DatabaseHelper db;
    private Consultation c;
    private boolean estPayee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_ajout);
        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        validerBtn = findViewById(R.id.valider_paie_btn);
        annulerBtn = findViewById(R.id.annuler_paie_btn);
        payNfcBtn = findViewById(R.id.pay_nfc_btn);
        consultationIdEdit = findViewById(R.id.consultation_id_edit);
        prixPaieEdit = findViewById(R.id.prix_paie_edit);
        etatPaieEdit = findViewById(R.id.etat_paie_edit);

        consultationIdEdit.addTextChangedListener(idListener);
        validerBtn.setOnClickListener(validerListener);
        payNfcBtn.setOnClickListener(payNfcListener);
        annulerBtn.setOnClickListener(annulerListener);

    }

    private View.OnClickListener annulerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
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
            c = db.getConsultationById(Integer.valueOf( (!consultationIdEdit.getText().toString().isEmpty())?consultationIdEdit.getText().toString():"-1" ));
            if(c == null){
                prixPaieEdit.setText("0");
                etatPaieEdit.setText("");
            }else{
                prixPaieEdit.setText(String.valueOf(c.getAmount()));
                if(db.getPaymentByIdConsultation(c.getId()) != null){ //payée
                    etatPaieEdit.setText("PAYEE");
                    estPayee = true;
                }else{
                    etatPaieEdit.setText("NON PAYEE");
                    estPayee = false;
                }
            }
        }
    };

    private View.OnClickListener validerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(c!=null && !estPayee){
                db.paymentInsertData(String.valueOf(c.getId()),String.valueOf(c.getAmount()));
                Toast.makeText(PaiementAjoutActivity.this, "Paiement éffectué", Toast.LENGTH_LONG).show();
                finish();
            }
            if(estPayee){
                Toast.makeText(PaiementAjoutActivity.this, "Déjà payée", Toast.LENGTH_LONG).show();
            }
        }
    };

    private View.OnClickListener payNfcListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(c!=null && c.getAmount()>0 && !estPayee) {
                Intent intent = new Intent(getBaseContext(), PaiementNfcActivity.class);
                intent.putExtra("com.example.docmobilepaydoctor.IdConsultation", Integer.valueOf(consultationIdEdit.getText().toString()));
                intent.putExtra("com.example.docmobilepaydoctor.Prix", Float.valueOf(prixPaieEdit.getText().toString()));
                startActivity(intent);
            }
            if(c != null && estPayee){
                Toast.makeText(PaiementAjoutActivity.this, "Déjà payée", Toast.LENGTH_LONG).show();
            }
            if(c==null){
                Toast.makeText(PaiementAjoutActivity.this, "ID="+consultationIdEdit.getText().toString()+" introuvable", Toast.LENGTH_LONG).show();
            }
        }
    };
}
