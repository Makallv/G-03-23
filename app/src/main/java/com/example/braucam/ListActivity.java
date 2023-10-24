package com.example.braucam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private Button newRideButton;
    private RideAdapter adapter;
    private EditText toTextField;
    private EditText fromTextField;
    private Button datePickerButton;
    private final List<Ride> rides = new ArrayList<>();
    private Calendar selectedDate = null;
    private Button resetDateButton;
    private DatabaseHelper databaseHelper;

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
        databaseHelper = DatabaseHelper.getInstance(this);

        rides.addAll(databaseHelper.getAvailableRides());

        listView = findViewById(R.id.listView);
        newRideButton = findViewById(R.id.newRideButton);

        fromTextField = findViewById(R.id.fromEditText);
        toTextField = findViewById(R.id.toEditText);

        datePickerButton = findViewById(R.id.dateTimePicker);
        resetDateButton = findViewById(R.id.resetDateTimePicker);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });


        fromTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateFilter();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        toTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateFilter();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        resetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDateButton.setVisibility(View.GONE);
                updateFromDateSelector(null);
            }
        });

        newRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddOfferActivity.class);
                addOfferLauncher.launch(intent);
            }
        });

        adapter = new RideAdapter(this, rides);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ride clickedRide = (Ride) parent.getItemAtPosition(position);

                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("CLICKED_RIDE", clickedRide);
                startActivity(intent);
            }
        });
    }

    private void showDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, monthOfYear, dayOfMonth);

                    updateFromDateSelector(selectedDate);
                    resetDateButton.setVisibility(View.VISIBLE);
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateFromDateSelector(Calendar selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());

        if (selectedDate != null) {
            datePickerButton.setText(dateFormat.format(selectedDate.getTime()));
            this.selectedDate = selectedDate;
        } else {
            datePickerButton.setText("From");
            this.selectedDate = null;
        }

        updateFilter();
    }

    private void updateFilter() {
        String fromFilter = fromTextField.getText().toString().toLowerCase(Locale.getDefault());
        String toFilter = toTextField.getText().toString().toLowerCase(Locale.getDefault());
        adapter.setFilters(fromFilter, toFilter, selectedDate);
        adapter.notifyDataSetChanged();
    }

    private void addRideToList(Ride ride) {
        rides.add(ride);
        adapter.notifyDataSetChanged();
    }

    private static Date createDate(int day, int month, int year, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute);
        return calendar.getTime();
    }
}
