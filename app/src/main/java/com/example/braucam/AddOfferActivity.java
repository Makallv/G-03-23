package com.example.braucam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddOfferActivity extends AppCompatActivity {

    private EditText startDestinationEditText;
    private EditText endDestinationEditText;
    private Button dateTimeEditText;
    private EditText additionalInfoEditText;
    private EditText priceEditText;
    private EditText maxSeatsEditText;
    private EditText carPlateEditText;
    private Button addOfferButton;
    private Button backButton;
    private DatabaseHelper databaseHelper;
    private Calendar selectedDateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        databaseHelper = DatabaseHelper.getInstance(this);
        carPlateEditText = findViewById(R.id.carPlateEditText);
        startDestinationEditText = findViewById(R.id.startDestinationEditText);
        endDestinationEditText = findViewById(R.id.endDestinationEditText);
        priceEditText = findViewById(R.id.priceEditText);
        additionalInfoEditText = findViewById(R.id.additionalInfoEditText);
        addOfferButton = findViewById(R.id.addOfferButton);
        backButton = findViewById(R.id.backButton);
        maxSeatsEditText = findViewById(R.id.maxSeatsEditText);

        dateTimeEditText = findViewById(R.id.dateTimeButton);
        dateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddOfferActivity.this, ListActivity.class);
                startActivity(intent);

            }
        });

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult();

            }
        });
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, monthOfYear);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    showTimePicker();
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    updateDateTimeEditText();
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateTimeEditText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault());
        dateTimeEditText.setText(dateFormat.format(selectedDateTime.getTime()));
    }

    private void sendResult() {
        String startDestination;
        String endDestination;
        Date dateTime;
        String additionalInfo;
        String carPlate;
        double price;
        int seats;
        int ownerId;
        int ID = 1;
        String bookerIDs = "";
        ownerId = MainActivity.getSession().getId();

        try{
            startDestination = startDestinationEditText.getText().toString();
            endDestination = endDestinationEditText.getText().toString();
            dateTime = selectedDateTime.getTime();
            additionalInfo = additionalInfoEditText.getText().toString();
            carPlate = carPlateEditText.getText().toString();
            price = Double.parseDouble(priceEditText.getText().toString());
            seats = Integer.parseInt(maxSeatsEditText.getText().toString());

        } catch(Exception e) {
            Toast.makeText(AddOfferActivity.this, "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Ride ride = new Ride(ID, ownerId, startDestination, endDestination, dateTime, carPlate, additionalInfo, price, seats, 0, bookerIDs);

        databaseHelper.addRide(ride);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_RIDE", ride);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}