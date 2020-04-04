package com.mbds.docmobilepaypatient;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NfcAdapter _nfcAdapter;
    private PendingIntent _pendingIntent;
    private IntentFilter[] _intentFilters;
    private final String _MIME_TYPE = "text/plain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (_nfcAdapter == null) {
            Toast.makeText(this, "Votre smartphone de support pas le NFC", Toast.LENGTH_LONG).show();
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
        }else{
            Toast.makeText(this, "Veuillez activer votre NFC", Toast.LENGTH_LONG).show();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_payment:
                            selectedFragment = new PaymentFragment();
                            break;
                        case R.id.navigation_historique:
                            selectedFragment = new HistoriqueFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();


                    return true;
                }
            };

    /*@Override
    protected void onResume()
    {
        super.onResume();
        _enableNdefExchangeMode();
    }

    private void _enableNdefExchangeMode()
    {
        String stringMessage = String.valueOf(soldeSuffisant);

        NdefMessage message = NFCUtils.getNewMessage(_MIME_TYPE, stringMessage.getBytes());

        _nfcAdapter.setNdefPushMessage(message, this);
        _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _intentFilters, null);
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

            montantEdit.setText(msg);
            float montant = 0;

            try {
                montant = Float.valueOf(msg);
                if(solde>montant) {
                    solde -= montant;
                    soldeSuffisant = true;
                    infoView.setTextColor(Color.GREEN);
                    infoView.setText("Prêt à transferer");
                }else{
                    soldeSuffisant = false;
                    infoView.setTextColor(Color.RED);
                    infoView.setText("Solde insuffisant");
                }
                super.onResume();
            }catch (Exception e){
                Toast.makeText(this, "error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }*/
}
