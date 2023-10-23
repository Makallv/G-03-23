package com.example.braucam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button addOfferButton;

    private Calendar selectedDateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        startDestinationEditText = findViewById(R.id.startDestinationEditText);
        endDestinationEditText = findViewById(R.id.endDestinationEditText);
        priceEditText = findViewById(R.id.priceEditText);
        additionalInfoEditText = findViewById(R.id.additionalInfoEditText);
        addOfferButton = findViewById(R.id.addOfferButton);

        dateTimeEditText = findViewById(R.id.dateTimeButton);
        dateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
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
        String startDestination = startDestinationEditText.getText().toString();
        String endDestination = endDestinationEditText.getText().toString();
        Date dateTime = selectedDateTime.getTime(); // Use the selectedDateTime directly
        String additionalInfo = additionalInfoEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());

        Ride ride = new Ride(startDestination, endDestination, dateTime, additionalInfo, price);

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