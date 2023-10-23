package com.example.braucam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RideAdapter extends BaseAdapter {

    private Context context;
    private List<Ride> rides;

    public RideAdapter(Context context, List<Ride> rides) {
        this.context = context;
        this.rides = rides;
    }

    @Override
    public int getCount() {
        return rides.size();
    }

    @Override
    public Object getItem(int position) {
        return rides.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addRide(Ride ride) {
        rides.add(ride);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ride_item_layout, parent, false);
        }

        TextView startDestinationTextView = view.findViewById(R.id.startDestinationTextView);
        TextView endDestinationTextView = view.findViewById(R.id.endDestinationTextView);
        TextView dateTimeTextView = view.findViewById(R.id.dateTimeTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        TextView additionalInfoTextView = view.findViewById(R.id.additionalInfoTextView);

        Ride ride = rides.get(position);

        if (ride != null) {
            startDestinationTextView.setText(ride.getStartDestination());
            endDestinationTextView.setText(ride.getEndDestination());
            dateTimeTextView.setText(ride.getDateAndTime().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault());
            dateTimeTextView.setText(dateFormat.format(ride.getDateAndTime()));

            priceTextView.setText(String.valueOf(ride.getPrice()));
            additionalInfoTextView.setText(ride.getAdditionalInfo());
        } else {
            //error or sumn
        }

        return view;
    }
}