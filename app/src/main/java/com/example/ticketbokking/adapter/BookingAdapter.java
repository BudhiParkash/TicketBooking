package com.example.ticketbokking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbokking.R;
import com.example.ticketbokking.model.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookViewHolder> {

    List<Booking> bookingList;
    Context context;

    public BookingAdapter(List<Booking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_booking_dashborad, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Booking Data = bookingList.get(position);

        String name = Data.getFlightName();
        String price = Data.getPrice();
        String deptime = Data.getDepatureTime();
        String  destime = Data.getDestinationTime();
        String from = Data.getFlightFrom();
        String to = Data.getFlightTo();
        String duration = Data.getDuration();


        holder.mFlightDepT.setText(deptime);
        holder.mFlightDesT.setText(destime);
        holder.mFlightName.setText(name);
        holder.mFlightF.setText(from);
        holder.mFlightPrice.setText(price);
        holder.mFlightT.setText(to);
        holder.mFligtDuration.setText(duration);



    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }



    public class BookViewHolder extends RecyclerView.ViewHolder {

        private TextView mFlightName;
        private TextView mFlightDepT;
        private TextView mFlightDesT;
        private TextView mFligtDuration;
        private TextView mFlightF;
        private TextView mFlightT;
        private TextView mFlightPrice;
        private TextView mBookStatus;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            mFlightName = itemView.findViewById(R.id.flight_name);
            mFlightDepT = itemView.findViewById(R.id.flight_depT);
            mFlightDesT = itemView.findViewById(R.id.flight_desT);
            mFligtDuration = itemView.findViewById(R.id.fligt_duration);
            mFlightF = itemView.findViewById(R.id.flight_F);
            mFlightT = itemView.findViewById(R.id.flight_t);
            mFlightPrice = itemView.findViewById(R.id.flight_price);
            mBookStatus = itemView.findViewById(R.id.book_status);

        }
    }
}
