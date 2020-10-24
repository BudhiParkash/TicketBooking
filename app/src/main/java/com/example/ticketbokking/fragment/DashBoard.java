package com.example.ticketbokking.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ticketbokking.R;
import com.example.ticketbokking.model.Ticket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DashBoard extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public DashBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("ProfileData" , MODE_PRIVATE);
        String number = preferences.getString("number" , "" );

        db.collection(number)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot DS : list){
                                DS.getData();
                                Log.d("getdata", DS.getId() + " => " + DS.getData());




                            }
                        }

                    }
                });


        return view;
    }
}