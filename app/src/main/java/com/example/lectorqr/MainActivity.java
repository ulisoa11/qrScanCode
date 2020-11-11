package com.example.lectorqr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button buttonCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        buttonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScan();
            }
        });
    }

    // Get the results:
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                this.openScan();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

   public final void openScan(){
       IntentIntegrator integrator = new IntentIntegrator((Activity) this);
       integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
       integrator.setPrompt("Lectura de codigo QR");
       integrator.setCameraId(0);
       integrator.setBeepEnabled(true);
       integrator.setBarcodeImageEnabled(true);
       integrator.initiateScan();
   }
}