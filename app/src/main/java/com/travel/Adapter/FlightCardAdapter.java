package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.Activity.BookFlightActivity;
import com.travel.Activity.BookHotelActivity;
import com.travel.Activity.DetailDestinationActivity;
import com.travel.Activity.DetailFlightActivity;
import com.travel.Model.AirportModel;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.FlightModel;
import com.travel.R;
import com.travel.Utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

public class FlightCardAdapter<T> extends RecyclerView.Adapter<FlightCardAdapter.FlightCardViewHolder> {
    private List<T> listItem;
    private List<AirportModel> listAirport;
    private Context context;

    public FlightCardAdapter(ArrayList<T> listItem, Context context, ArrayList<AirportModel> airports) {
        this.listItem = listItem;
        this.context = context;
        this.listAirport = airports;
    }

    @NonNull
    @Override
    public FlightCardAdapter.FlightCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flight, parent, false);
        return new FlightCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightCardAdapter.FlightCardViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof FlightModel) {
            bindDestinationModel(holder, (FlightModel) item);
        }
    }

    private void bindDestinationModel(FlightCardViewHolder holder, FlightModel item) {
        holder.airportDeparture.setText(listAirport.stream().filter(airport -> airport.getAirportCode().equals(item.getDepartureAirportCode())).findFirst().get().getDestination().getName());
        holder.airportArrival.setText(listAirport.stream().filter(airport -> airport.getAirportCode().equals(item.getArrivalAirportCode())).findFirst().get().getDestination().getName());
        holder.departTime.setText(DateTimeHelper.convertTimeStampToStringFormat(item.getDepartureTime(), "HH:mm"));
        holder.arrivalTime.setText(DateTimeHelper.convertTimeStampToStringFormat(item.getArrivalTime(), "HH:mm"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFlightActivity.class);
                intent.putExtra("flightId", item.getFlightId());
                context.startActivity(intent);
            }
        });

        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookFlightActivity.class);
                intent.putExtra("flightId", item.getFlightId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class FlightCardViewHolder extends RecyclerView.ViewHolder {
        TextView airportDeparture, airportArrival, departTime, arrivalTime;
        Button btnBook;

        public FlightCardViewHolder(@NonNull View itemView) {
            super(itemView);
            airportDeparture = itemView.findViewById(R.id.airportDeparture);
            airportArrival = itemView.findViewById(R.id.airportArrival);
            departTime = itemView.findViewById(R.id.departTime);
            arrivalTime = itemView.findViewById(R.id.arrivalTime);
            btnBook = itemView.findViewById(R.id.btnBooking);
        }
    }
}
