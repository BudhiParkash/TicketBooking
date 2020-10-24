package com.example.ticketbokking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketbokking.BookScreen;
import com.example.ticketbokking.R;
import com.example.ticketbokking.model.Ticket;

import java.util.List;


public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private Context context;
    private List<Ticket> contactList;
    private TicketsAdapterListener listener;
    TextView airlinrName;
    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView airlineName = itemView.findViewById(R.id.airline_name);
        TextView stops =itemView.findViewById(R.id.number_of_stops);
        TextView seats =itemView.findViewById(R.id.number_of_seats);
        TextView departure = itemView.findViewById(R.id.departure);
        TextView arrival = itemView.findViewById(R.id.arrival);
        TextView duration =itemView.findViewById(R.id.duration);
        TextView price = itemView.findViewById(R.id.price);




        public MyViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTicketSelected(contactList.get(getAdapterPosition()));

                    Ticket tickets = contactList.get(getAdapterPosition());

                    //Get Tikets data
                    //Intent pass
                    //New activity booking
                    Intent intent = new Intent(context , BookScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("price" , tickets.getPrice().getPrice()+" Rs");
                    bundle.putString("name" , tickets.getAirline().getName());
                    bundle.putString("deptime" , tickets.getDeparture());
                    bundle.putString("destime" , tickets.getArrival());
                    bundle.putString("duration" , tickets.getDuration());
                    bundle.putString("from" , tickets.getFrom());
                    bundle.putString("to" , tickets.getTo());
                    intent.putExtras(bundle);
                    context.startActivity(intent);




                }
            });
        }
    }

    public TicketAdapter(Context context, List<Ticket> contactList, TicketsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Ticket ticket = contactList.get(position);


        holder.airlineName.setText(ticket.getAirline().getName());

        holder.departure.setText(ticket.getDeparture() + " Dep");
        holder.arrival.setText(ticket.getArrival() + " Dest");

        holder.duration.setText(ticket.getFlightNumber());
        holder.duration.append(", " + ticket.getDuration());
        holder.stops.setText(ticket.getNumberOfStops() + " Stops");

        if (!TextUtils.isEmpty(ticket.getInstructions())) {
            holder.duration.append(", " + ticket.getInstructions());
        }

        if (ticket.getPrice() != null) {
            holder.price.setText("₹" + String.format("%.0f", ticket.getPrice().getPrice()));
            holder.seats.setText(ticket.getPrice().getSeats() + " Seats");
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface TicketsAdapterListener {
        void onTicketSelected(Ticket contact);
    }
}
