package com.example.docmobilepaydoctor.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docmobilepaydoctor.R;
import com.example.docmobilepaydoctor.helpers.DatabaseHelper;
import com.example.docmobilepaydoctor.modele.Account;
import com.example.docmobilepaydoctor.utils.NFCUtils;

import java.util.List;

public class PaiementNfcActivity extends AppCompatActivity {
    private Button annulerBtn;
    private TextView consultationIdView;
    private TextView prixView;
    private int idConsultation;
    private float prix;
    private DatabaseHelper db;

    private NfcAdapter _nfcAdapter;
    private PendingIntent _pendingIntent;
    private IntentFilter[] _intentFilters;
    private final String _MIME_TYPE = "text/plain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_nfc);
        db = new DatabaseHelper(this);

        annulerBtn = findViewById(R.id.annuler_nfc_btn);
        consultationIdView = findViewById(R.id.cons_nfc_id_text);
        prixView = findViewById(R.id.paie_prix_nfc_text);

        idConsultation = getIntent().getIntExtra("com.example.docmobilepaydoctor.IdConsultation",0);
        prix = getIntent().getFloatExtra("com.example.docmobilepaydoctor.Prix",0);
        consultationIdView.setText(String.valueOf(idConsultation));
        prixView.setText(String.valueOf(prix));
        annulerBtn.setOnClickListener(annulerListener);

        _nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (_nfcAdapter == null) {
            Toast.makeText(this, "Veuillez activer votre NFC", Toast.LENGTH_LONG).show();
            finish();
        }

        if (_nfcAdapter.isEnabled())
        {
            _pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            try
            {
                ndefDetected.addDataType(_MIME_TYPE);
            } catch (IntentFilter.MalformedMimeTypeException e)
            {
                Log.e(this.toString(), e.getMessage());
            }

            _intentFilters = new IntentFilter[] { ndefDetected };
        }
    }

    private View.OnClickListener annulerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private boolean ajouterPaiement(){
        try {
            Account compte = db.getAccountById(1);//account du docteur
            db.paymentInsertData(String.valueOf(idConsultation), String.valueOf(prix));
            db.updateAccount("1",String.valueOf(prix+compte.getBank()));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void _enableNdefExchangeMode()
    {
        String stringMessage = prixView.getText().toString();
        NdefMessage message = NFCUtils.getNewMessage(_MIME_TYPE, stringMessage.getBytes());
        _nfcAdapter.setNdefPushMessage(message, this);
        _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _intentFilters, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _enableNdefExchangeMode();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
        {
            List<String> msgs = NFCUtils.getStringsFromNfcIntent(intent);
            String msg="";
            for(String m:msgs)
                msg+=m;

            try{
                Intent newIntent = new Intent (getBaseContext(), PaiementActivity.class);
                if(msg.compareTo("true")==0){
                    if(ajouterPaiement())
                        Toast.makeText(PaiementNfcActivity.this, "Paiment éffectué", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(PaiementNfcActivity.this, "Oups! Paiement non éfféctué", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Erreur paiement", Toast.LENGTH_LONG).show();
                }
                startActivity (newIntent);
                finish();
            }catch (Exception e){
                Toast.makeText(this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
