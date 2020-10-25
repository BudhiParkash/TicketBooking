package com.example.ticketbokking.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ticketbokking.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {
    View view;
    private CircleImageView mUserPic;
    private TextView mUsernames;
    private TextView mUseremail;
    private TextView mUserPhone;
    private TextView mUsergender;
    private Button mHelpbtn;


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView();
        SharedPreferences preferences = getActivity().getSharedPreferences("ProfileData" , Context.MODE_PRIVATE);
        String number = preferences.getString("number" ,"");
        String name = preferences.getString("name" , "");
        String Gender = preferences.getString("gender" ,"");
        String email = preferences.getString("email","");

        mUseremail.setText(email);
        mUsergender.setText(Gender);
        mUsernames.setText(name);
        mUserPhone.setText(number);

        mHelpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + "999999999")));
            }
        });



        return view;
    }

    private void initView() {
        mUsernames = view.findViewById(R.id.usernames);
        mUseremail = view.findViewById(R.id.useremail);
        mUserPhone = view.findViewById(R.id.userPhone);
        mUsergender = view.findViewById(R.id.usergender);
        mHelpbtn =view.findViewById(R.id.btnhelp);
    }
}