package com.example.retry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goSecondActivityButton = findViewById(R.id.toSecond);
        goSecondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        Button dialogButton = findViewById(R.id.dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                CheckBox checkbox1 = dialogView.findViewById(R.id.checkbox_1);
                CheckBox checkbox2 = dialogView.findViewById(R.id.checkbox_2);
                CheckBox checkbox3 = dialogView.findViewById(R.id.checkbox_3);

                checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(MainActivity.this, "Marks Cimermanis checked", Toast.LENGTH_SHORT).show();
                        }

                        if (!isChecked) {
                            Toast.makeText(MainActivity.this, "Marks Cimermanis unchecked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(MainActivity.this, "Kristians Zondaks checked", Toast.LENGTH_SHORT).show();
                        }

                        if (!isChecked) {
                            Toast.makeText(MainActivity.this, "Kristians Zondaks unchecked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Toast.makeText(MainActivity.this, "Ieva Madara Glavecka checked", Toast.LENGTH_SHORT).show();
                        }

                        if (!isChecked) {
                            Toast.makeText(MainActivity.this, "Ieva Madara Glavecka unchecked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setTitle("3. Groups Dialog");
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.findViewById(R.id.dialog_close_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogCloseButtonClick(dialog);
                    }
                });
                dialog.findViewById(R.id.dialog_ok_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogOkButtonClick();
                    }
                });
                dialog.show();
            }
        });
    }

    public void onDialogCloseButtonClick(AlertDialog dialog) {
        dialog.dismiss();
    }

    public void onDialogOkButtonClick() {
        Toast.makeText(MainActivity.this, "You Clicked OK", Toast.LENGTH_SHORT).show();
    }
}