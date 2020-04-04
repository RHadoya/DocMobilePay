package com.mbds.docmobilepaypatient;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.docmobilepaypatient.utils.NFCUtils;

import java.util.List;

public class PaymentFragment extends Fragment {

    private EditText montantConsultation;
    private TextView infoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment,container,false);
        montantConsultation = view.findViewById(R.id.montantconsultation);
        infoView = view.findViewById(R.id.info_view);

        return view;
    }


}
