package com.example.braucam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private Button newRideButton;
    private RideAdapter adapter;
    private final List<Ride> rides = new ArrayList<>();

    private final ActivityResultLauncher<Intent> addOfferLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Ride newRide = (Ride) data.getSerializableExtra("NEW_RIDE");
                        addRideToList(newRide);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        newRideButton = findViewById(R.id.newRideButton);

        newRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddOfferActivity.class);
                addOfferLauncher.launch(intent);
            }
        });

        // Initialize the list of rides
        rides.addAll(getAvailableRides());

        // Create a new adapter for the list of rides
        adapter = new RideAdapter(this, rides);

        // Set the adapter for the list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked ride
                Ride clickedRide = (Ride) parent.getItemAtPosition(position);

                // Start DetailActivity and pass the clickedRide
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("CLICKED_RIDE", clickedRide);
                startActivity(intent);
            }
        });
    }

    private void addRideToList(Ride ride) {
        rides.add(ride);
        adapter.notifyDataSetChanged();
    }

    public static List<Ride> getAvailableRides() {
        return new ArrayList<>(List.of(
                new Ride("San Francisco, CA", "Los Angeles, CA", createDate(12, 10, 2023, 12, 0), "HiHi", 100),
                new Ride("Los Angeles, CA", "San Francisco, CA", createDate(25, 10, 2023, 18, 0), "HeHe", 80),
                new Ride("New York, NY", "Washington, DC", createDate(13, 10, 2023, 6, 0), "HoHo", 120),
                new Ride("Washington, DC", "New York, NY", createDate(21, 10, 2023, 9, 30), "HaHa", 90)
        ));
    }

    private static Date createDate(int day, int month, int year, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute);
        return calendar.getTime();
    }
}
