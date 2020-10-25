package com.example.ticketbokking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.ticketbokking.FlightList;
import com.example.ticketbokking.R;


public class Home extends Fragment {


    private Toolbar mHomeToolbar;
    private Spinner mFromCity;
    private Spinner mToCity;
    private Button mSearchRoute;
    View view;
    String[] CityFrom={"---Select City---","Hisar","Delhi","Gujrat","Amritsar","Guwahati","Odisha","Kerala","Bhopal" , "Jaipur" , "Mumbai" , "Kolkata" ,"Lucknow"};
    String[] CityTo={"---Select City---","Hisar","Delhi","Gujrat","Amritsar","Guwahati","Odisha","Kerala","Bhopal" , "Jaipur" , "Mumbai","Kolkata" ,"Lucknow"};

    public Home() {
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
      view  = inflater.inflate(R.layout.fragment_home, container, false);
      initView();

        ArrayAdapter FromArrycity = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,CityFrom);
        FromArrycity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFromCity.setAdapter(FromArrycity);


        ArrayAdapter ToArrayCity = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,CityTo);
        ToArrayCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mToCity.setAdapter(ToArrayCity);

        mSearchRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFromCity.getSelectedItem().toString().equals("---Select City---")){
                    Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(mToCity.getSelectedItem().toString().equals("---Select City---")){
                    Toast.makeText(getActivity(), "Please Select City", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String fcity = mFromCity.getSelectedItem().toString();
                    String tcity = mToCity.getSelectedItem().toString();

                    Intent intent = new Intent(getActivity() , FlightList.class);

                    intent.putExtra("fcity" , fcity);
                    intent.putExtra("tcity" , tcity);
                    startActivity(intent);


                }


            }
        });

      return view;
    }

    private void initView() {
        mHomeToolbar = view.findViewById(R.id.home_toolbar);
        mFromCity = view.findViewById(R.id.from_city);
        mToCity = view.findViewById(R.id.to_city);
        mSearchRoute = view.findViewById(R.id.search_route);
    }
}