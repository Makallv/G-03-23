package com.practical2.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dialogButton = findViewById(R.id.btnDialog);

        button = (Button) findViewById(R.id.btnAct2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    private void openDialog() {
        final CharSequence[] groupMembers = {"Ieva Madara Glavecka", "Kristiāns Žondaks", "Marks Cimermanis"};
        boolean[] checkedItems = {false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("3. Group's Dialog");
        builder.setMultiChoiceItems(groupMembers, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, groupMembers[which] + " checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, groupMembers[which] + " unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "You clicked OK", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "You closed dialog", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
}