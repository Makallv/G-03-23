package com.example.practicalwork1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.mainText);
        textView.setText("This is practical work from Group 3\n\n");
        textView.append(getString(R.string.app_name));
        textView.append("\nThis application was developed by: ");
        textView.append(getString(R.string.dev_name));
    }
}