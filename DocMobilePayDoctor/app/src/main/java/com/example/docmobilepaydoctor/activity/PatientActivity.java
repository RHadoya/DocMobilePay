package com.example.docmobilepaydoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docmobilepaydoctor.R;
import com.example.docmobilepaydoctor.helpers.DatabaseHelper;
import com.example.docmobilepaydoctor.modele.Consultation;
import com.example.docmobilepaydoctor.modele.Person;

import java.util.List;

public class PatientActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private ListView mListView;
    private List<Person> patients;
    private String[] patientsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        mListView = findViewById(R.id.listViewPat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabPat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PatientActivity.this,"Indisponnible",Toast.LENGTH_LONG).show();
            }
        });

        init();
    }

    public void init(){
        if(db == null)
            db = new DatabaseHelper(this);
        patients = db.getAllPatient();
        if(patients.size()>0){
            patientsArray = new String[patients.size()];
            for(int i=0;i<patients.size();i++) {
                patientsArray[i] = "ID: "+patients.get(i).getId() + " | " + patients.get(i).getFirstName()+" "+patients.get(i).getName();
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(PatientActivity.this,android.R.layout.simple_list_item_1, patientsArray);
            mListView.setAdapter(adapter);
        }
    }
}
