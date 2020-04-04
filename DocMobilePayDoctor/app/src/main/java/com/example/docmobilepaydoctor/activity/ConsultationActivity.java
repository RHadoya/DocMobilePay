package com.example.docmobilepaydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.docmobilepaydoctor.R;
import com.example.docmobilepaydoctor.helpers.DatabaseHelper;
import com.example.docmobilepaydoctor.modele.Consultation;
import com.example.docmobilepaydoctor.modele.Person;

import java.util.List;
import java.util.concurrent.CompletionService;

public class ConsultationActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private ListView mListView;
    private List<Consultation> consultations;
    private String[] consultationsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultations);
        mListView = findViewById(R.id.listView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ConsultationAjoutActivity.class);
                startActivity (intent);
            }
        });

        init();

    }

    public void init(){
        if(db == null)
            db = new DatabaseHelper(this);
        consultations = db.getAllConsultation();
        if(consultations.size()>0){
            consultationsArray = new String[consultations.size()];
            for(int i=0;i<consultations.size();i++) {
                Person p = db.getPersonById(consultations.get(i).getIdPatient());
                consultationsArray[i] = "ID: "+consultations.get(i).getId() + " | Patient: " + p.getFirstName()+" "+p.getName()+" | "+consultations.get(i).getAmount();
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConsultationActivity.this,android.R.layout.simple_list_item_1, consultationsArray);
            mListView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
