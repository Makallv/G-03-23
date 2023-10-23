package com.example.braucam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve the clicked Ride from the intent
        Intent intent = getIntent();
        if (intent != null) {
            Ride clickedRide = (Ride) intent.getSerializableExtra("CLICKED_RIDE");

            // Use the clickedRide object to populate your UI
            if (clickedRide != null) {
                // Example: Display detailed information in TextViews or other UI components
                TextView startDestinationTextView = findViewById(R.id.startDestinationTextView);
                TextView endDestinationTextView = findViewById(R.id.endDestinationTextView);
                TextView dateTimeTextView = findViewById(R.id.dateTimeTextView);
                TextView priceTextView = findViewById(R.id.priceTextView);
                TextView additionalInfoTextView = findViewById(R.id.additionalInfoTextView);

                startDestinationTextView.setText(clickedRide.getStartDestination());
                endDestinationTextView.setText(clickedRide.getEndDestination());
                dateTimeTextView.setText(clickedRide.getDateAndTime().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault());
                dateTimeTextView.setText(dateFormat.format(clickedRide.getDateAndTime()));
                priceTextView.setText(String.valueOf(clickedRide.getPrice()));
                additionalInfoTextView.setText(clickedRide.getAdditionalInfo());

                Button backToListButton = findViewById(R.id.backToListButton);
                backToListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                // Set up click listener for the "Some Action" button
                Button bookingButton = findViewById(R.id.bookingButton);
                bookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetailActivity.this, "Booked!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}