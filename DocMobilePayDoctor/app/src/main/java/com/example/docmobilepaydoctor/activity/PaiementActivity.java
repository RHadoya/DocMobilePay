package com.example.docmobilepaydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docmobilepaydoctor.R;
import com.example.docmobilepaydoctor.helpers.DatabaseHelper;
import com.example.docmobilepaydoctor.modele.Consultation;
import com.example.docmobilepaydoctor.modele.Payment;
import com.example.docmobilepaydoctor.modele.Person;

import java.util.List;

public class PaiementActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private ListView mListView;
    private List<Payment> paiements;
    private String[] paiementsArray;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiements);
        mListView = findViewById(R.id.listViewP);

        Toolbar toolbar = findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabP);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PaiementAjoutActivity.class);
                startActivity (intent);
            }
        });

        init();
    }

    public void init(){
        if(db == null)
            db = new DatabaseHelper(this);
        paiements = db.getAllPayment();
        if(paiements.size()>0){
            paiementsArray = new String[paiements.size()];
            for(int i=0;i<paiements.size();i++) {
                Consultation c = db.getConsultationById(paiements.get(i).getIdConsultation());
                Person p = db.getPersonById(c.getIdPatient());
                paiementsArray[i] = p.getFirstName()+" "+p.getName()+" | "+paiements.get(i).getAmount()+" Euro";
            }
            adapter = new ArrayAdapter<String>(PaiementActivity.this,android.R.layout.simple_list_item_1, paiementsArray);
            mListView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
