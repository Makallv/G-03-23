package com.example.braucam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText password1EditText;
    private EditText password2EditText;
    private EditText usernameEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        password1EditText = findViewById(R.id.passwordEditText);
        password2EditText = findViewById(R.id.password2EditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password1 = password1EditText.getText().toString();
                String password2 = password2EditText.getText().toString();
                String username = usernameEditText.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password1.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Entered passwords don't match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}