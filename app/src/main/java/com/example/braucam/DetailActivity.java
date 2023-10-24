package com.example.braucam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        databaseHelper = DatabaseHelper.getInstance(this);

        Intent intent = getIntent();
        if (intent != null) {
            Ride clickedRide = (Ride) intent.getSerializableExtra("CLICKED_RIDE");

            if (clickedRide != null) {
                TextView detailedTextView = findViewById(R.id.detailedView);
                TextView additionalInfoTextView = findViewById(R.id.additionalInfoTextView);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault());
                String formattedTime = dateFormat.format(clickedRide.getDateAndTime());
                detailedTextView.setText(formattedTime + "\n" +
                        clickedRide.getStartDestination()+ " - " +
                        clickedRide.getEndDestination()+ " (" +
                        String.format(Locale.getDefault(), "%.2f", clickedRide.getPrice()) + ") Eur");
                additionalInfoTextView.setText(clickedRide.getAdditionalInfo());

                Button backToListButton = findViewById(R.id.backToListButton);
                backToListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("UPDATED_RIDE", clickedRide);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });

                Button bookingButton = findViewById(R.id.bookingButton);
                bookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("SessionIDS", MainActivity.getSession().getId() + "");
                        Log.d("SessionIDS", clickedRide.getOwnerId() + "");
                        String toastText = databaseHelper.addBooking(clickedRide);
                        Toast.makeText(DetailActivity.this, toastText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}