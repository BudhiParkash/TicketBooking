package com.example.ticketbokking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookScreen extends AppCompatActivity {

    private Toolbar mToolbarbook;
    private TextView mFName;
    private TextView mFfrom;
    private TextView mFto;
    private TextView mFtimeDep;
    private TextView mFtimedes;
    private TextView mFduration;
    private TextView mFprice;
    private Button mbtnBooking;
    private String TAG = "Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);
        initView();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharedPreferences preferences = getSharedPreferences("ProfileData" , MODE_PRIVATE);
        String number = preferences.getString("number" , "" );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String Fname = bundle.getString("name");
            String Ffrom = bundle.getString("from");
            String Fto = bundle.getString("to");
            String FDep = bundle.getString("deptime");
            String FDes = bundle.getString("destime");
            String Fdeuration = bundle.getString("duration");
            String Fprice = bundle.getString("price");

            mFName.setText(Fname);
            mFfrom.setText(Ffrom);
            mFto.setText(Fto);
            mFtimeDep.setText(FDep);
            mFtimedes.setText(FDes);
            mFduration.setText(Fdeuration);
            mFprice.setText(Fprice);

            mbtnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Map<String, Object> booking = new HashMap<>();
                    booking.put("Flight Name", Fname);
                    booking.put("Flight From", Ffrom);
                    booking.put("Flight To", Fto);
                    booking.put("Depature Time", FDep);
                    booking.put("Destination Time", FDes);
                    booking.put("Duration", Fdeuration);
                    booking.put("Price", Fprice);


                    db.collection(number)
                            .add(booking)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(BookScreen.this, "Data Added SuccessFully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
            });
        }


    }

    private void initView() {
        mToolbarbook = findViewById(R.id.toolbarbook);
        mFName = findViewById(R.id.FName);
        mFfrom = findViewById(R.id.Ffrom);
        mFto = findViewById(R.id.Fto);
        mFtimeDep = findViewById(R.id.FtimeDep);
        mFtimedes = findViewById(R.id.Ftimedes);
        mFduration = findViewById(R.id.Fduration);
        mFprice = findViewById(R.id.Fprice);
        mbtnBooking = findViewById(R.id.btnbooking);
    }
}