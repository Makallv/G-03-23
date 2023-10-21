package com.example.braucam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddOfferActivity extends AppCompatActivity {

    private EditText startDestinationEditText;
    private EditText endDestinationEditText;
    private EditText dateTimeEditText;
    private EditText additionalInfoEditText;
    private EditText priceEditText;
    private Button addOfferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        startDestinationEditText = findViewById(R.id.startDestinationEditText);
        endDestinationEditText = findViewById(R.id.endDestinationEditText);
        dateTimeEditText = findViewById(R.id.dateTimeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        additionalInfoEditText = findViewById(R.id.additionalInfoEditText);
        addOfferButton = findViewById(R.id.addOfferButton);

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult();
            }
        });
    }

    private void sendResult() {
        String startDestination = startDestinationEditText.getText().toString();
        String endDestination = endDestinationEditText.getText().toString();
        String dateTime = dateTimeEditText.getText().toString();
        String additionalInfo = additionalInfoEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());

        Log.d("AddOfferActivity", "startDestination: " + startDestination);
        Log.d("AddOfferActivity", "endDestination: " + endDestination);
        Log.d("AddOfferActivity", "dateTime: " + dateTime);
        Log.d("AddOfferActivity", "additionalInfo: " + additionalInfo);
        Log.d("AddOfferActivity", "price: " + price);

        Ride ride = new Ride(startDestination, endDestination, dateTime, additionalInfo, price);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_RIDE", ride);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}