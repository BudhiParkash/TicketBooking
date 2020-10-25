package com.example.ticketbokking.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbokking.R;
import com.example.ticketbokking.adapter.BookingAdapter;
import com.example.ticketbokking.model.Booking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DashBoard extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Toolbar mDashToolbar;
    private RecyclerView mDashboradrecycle;
    private TextView mTextDashboard;
    View view;
    ProgressBar mDashProgressbar;

    BookingAdapter adapter;
    List<Booking> bookingdata;

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
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        initView();
        SharedPreferences preferences = getActivity().getSharedPreferences("ProfileData", MODE_PRIVATE);
        String number = preferences.getString("number", "");

        mDashboradrecycle.setHasFixedSize(true);
        mDashboradrecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        bookingdata = new ArrayList<>();

        mDashProgressbar.setVisibility(View.VISIBLE);
        db.collection(number)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot DS : list) {

                                Booking data =DS.toObject(Booking.class);
                                bookingdata.add(data);

                            }

                            adapter = new BookingAdapter(bookingdata, getActivity());
                            mDashboradrecycle.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            mTextDashboard.setVisibility(View.GONE);
                            mDashProgressbar.setVisibility(View.GONE);
                        }
                        else {
                            mTextDashboard.setVisibility(View.VISIBLE);
                            mDashProgressbar.setVisibility(View.GONE);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Please Check your Internet", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void initView() {
        mDashToolbar = view.findViewById(R.id.dash_toolbar);
        mDashboradrecycle = view.findViewById(R.id.dashboradrecycle);
        mTextDashboard = view.findViewById(R.id.text_dashboard);
        mDashProgressbar =view.findViewById(R.id.dashProgressbar);
    }
}