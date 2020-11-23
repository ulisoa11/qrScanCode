package com.example.lectorqr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "etiqueta";
    @BindView(R.id.button)
    Button buttonCamara;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/assistence/uid/");
    DatabaseReference myRefUsers = database.getReference("/users/uid/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        buttonCamara.setOnClickListener(view ->
                openScan()
        );
    }

    // Get the results:
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Se ha autenticado como: " + result.getContents(), Toast.LENGTH_LONG).show();
                this.openScan();
                readDatabaseUser(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

   public final void openScan(){
       IntentIntegrator integrator = new IntentIntegrator((Activity) this);
       integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
       //integrator.setPrompt("Lectura de codigo QR");
       integrator.setCameraId(0);
       integrator.setBarcodeImageEnabled(true);
       integrator.initiateScan();
   }

    public boolean readDatabaseUser(String result){
        myRefUsers.child(result).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + map);
                if(map!=null){
                    //Log.d(TAG, "Se encontró registro: " + map);
                    insertDateToFirebase(result);
                }else {
                    Log.d(TAG, "NO se encontró registro: " + result);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return true;
    }

    public void insertDateToFirebase(String uid ) {
        myRef.child(uid).child(currentDate()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Asistencia post = dataSnapshot.getValue(Asistencia.class);
                if(post ==null){
                    insertInput(uid);
                }else{
                    insertOutput(uid);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void insertInput(String uid){
        myRef.child(uid).child(currentDate()).child("input").setValue(currentTime());
        myRef.child(uid).child(currentDate()).child("output").setValue(currentTime());
    }

    public void insertOutput(String uid){
        myRef.child(uid).child(currentDate()).child("output").setValue(currentTime());

    }

    public String currentDate(){
        //obtener fecha del dia
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        String stringdate,currentDateandTime;
        stringdate=  dt.format(new Date());
        return  stringdate;
    }

    public String currentTime(){
        //obtener hora
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(new Date());
    }
}