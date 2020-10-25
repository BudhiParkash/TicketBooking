package com.example.ticketbokking;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    String Fname, Ffrom,Fto,FDep,FDes,Fdeuration,Fprice,number;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_screen);
        initView();

        SharedPreferences preferences = getSharedPreferences("ProfileData" , MODE_PRIVATE);
        number = preferences.getString("number" , "" );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
             Fname = bundle.getString("name");
             Ffrom = bundle.getString("from");
             Fto = bundle.getString("to");
             FDep = bundle.getString("deptime");
             FDes = bundle.getString("destime");
             Fdeuration = bundle.getString("duration");
             Fprice = bundle.getString("price");

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
                    createDialog();
                }
            });
        }


    }

    private void createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(this));
        // set title
        alertDialogBuilder.setTitle("Book Ticket");
        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure  to book this Ticket")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map<String, Object> booking = new HashMap<>();
                        booking.put("FlightName", Fname);
                        booking.put("FlightFrom", Ffrom);
                        booking.put("FlightTo", Fto);
                        booking.put("DepatureTime", FDep);
                        booking.put("DestinationTime", FDes);
                        booking.put("Duration", Fdeuration);
                        booking.put("Price", Fprice);


                        db.collection(number)
                                .add(booking)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(BookScreen.this, "Book Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                      finish();


                    }
                })
                .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
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