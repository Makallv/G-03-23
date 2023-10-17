import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prat3.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button readPreferencesButton = findViewById(R.id.readPreferencesButton);
        TextView resultText = findViewById(R.id.textField);
        SharedPreferences preferences = this.getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
        readPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read text from SharedPreferences
                String savedText = preferences.getString("text", "");
                Log.d("fetchedText",savedText);

                if (!savedText.isEmpty()) {
                    resultText.setText(savedText);
                } else {
                    Toast.makeText(SecondActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button goFirstActivityButton = findViewById(R.id.backButton);
        goFirstActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finishAffinity();
                startActivity(intent);
            }
        });
    }
}