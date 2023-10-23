package com.example.braucam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText password1EditText;
    private EditText password2EditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        password1EditText = findViewById(R.id.passwordEditText);
        password2EditText = findViewById(R.id.password2EditText);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phoneText = phoneEditText.getText().toString();
                String password1 = password1EditText.getText().toString();
                String password2 = password2EditText.getText().toString();

                if (username.isEmpty() || name.isEmpty() || username.isEmpty() || surname.isEmpty() || email.isEmpty() || phoneText.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneText.length() != 8) {
                    Toast.makeText(RegisterActivity.this, "Invalid phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int phone = 0;
                try {
                    phone = Integer.parseInt(phoneText);
                } catch (NumberFormatException e) {
                    Toast.makeText(RegisterActivity.this, "Invalid phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password1.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Entered passwords don't match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(RegisterActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }
}