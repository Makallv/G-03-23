package com.example.braucam;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RideAdapter extends BaseAdapter {

    private Context context;
    private List<Ride> rides;
    private String filterFrom;
    private String filterTo;
    private Calendar filterDate;
    public RideAdapter(Context context, List<Ride> rides) {
        this.context = context;
        this.rides = rides;
        this.filterFrom = "";
        this.filterTo = "";
        this.filterDate = null;
    }

    public void setFilters(String from, String to, Calendar date) {
        filterFrom = from.toLowerCase();
        filterTo = to.toLowerCase();
        filterDate = date;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        for (Ride ride : rides) {
            Log.d("RideAdapter", filterFrom);
            String startDestination = ride.getStartDestination().toLowerCase(Locale.getDefault());
            String endDestination = ride.getEndDestination().toLowerCase(Locale.getDefault());

            if ((filterFrom.isEmpty() || startDestination.contains(filterFrom))
                    && (filterTo.isEmpty() || endDestination.contains(filterTo))
                    && (filterDate == null || isSameDate(ride.getDateAndTime(), filterDate.getTime()))) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        int currentIndex = -1;
        for (Ride ride : rides) {
            String startDestination = ride.getStartDestination().toLowerCase(Locale.getDefault());
            String endDestination = ride.getEndDestination().toLowerCase(Locale.getDefault());

            if ((filterFrom.isEmpty() || startDestination.contains(filterFrom))
                    && (filterTo.isEmpty() || endDestination.contains(filterTo))
                    && (filterDate == null || isSameDate(ride.getDateAndTime(), filterDate.getTime()))) {
                currentIndex++;
                if (currentIndex == position) {
                    return ride;
                }
            }
        }
        return null;
    }
    private boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addRide(Ride ride) {
        rides.add(ride);
        notifyDataSetChanged();
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

        Ride ride = (Ride) getItem(position);

        if (ride != null) {
            startDestinationTextView.setText(ride.getStartDestination());
            endDestinationTextView.setText(ride.getEndDestination());
            dateTimeTextView.setText(ride.getDateAndTime().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault());
            dateTimeTextView.setText(dateFormat.format(ride.getDateAndTime()));

            String formattedPrice = String.format(Locale.getDefault(), "%.2f", ride.getPrice());
            priceTextView.setText(formattedPrice + " Eur");
            additionalInfoTextView.setText(ride.getAdditionalInfo());

            if (ride.getReservedSeats() == ride.getSeats()) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.bookedColor));
            } else {
                // Reset the background color if not booked
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        } else {
            //error or sumn
        }

        return view;
    }
    public void updateBookingStatus(int position) {
        Ride ride = (Ride) getItem(position);
        if (ride != null && ride.getReservedSeats() == ride.getSeats()) {

            notifyDataSetChanged();
        }
    }
}