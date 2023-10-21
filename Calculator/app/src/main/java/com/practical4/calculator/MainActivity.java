package com.practical4.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private String currentInput = "";
    private double memoryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "MC":
                memoryValue = 0;
                break;
            case "MR":
                currentInput = String.valueOf(memoryValue);
                break;
            case "MS":
                memoryValue = Double.parseDouble(currentInput);
                break;
            case "=":
                try {
                    currentInput = String.valueOf(eval(currentInput));
                } catch (Exception e) {
                    currentInput = "Error";
                }
                break;
            case "C":
                currentInput = ""; // Clear the display
                break;
            default:
                currentInput += buttonText;
                break;
        }

        display.setText(currentInput);
    }

    // Evaluate the mathematical expression
    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean isDigitChar() {
                return (ch >= '0' && ch <= '9') || ch == '.';
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (ch == '+') {
                        nextChar();
                        x += parseTerm();
                    } else if (ch == '-') {
                        nextChar();
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (ch == '*') {
                        nextChar();
                        x *= parseFactor();
                    } else if (ch == '/') {
                        nextChar();
                        x /= parseFactor();
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (ch == '+') {
                    nextChar();
                    return parseFactor();
                }
                if (ch == '-') {
                    nextChar();
                    return -parseFactor();
                }
                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while (isDigitChar()) nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                return x;
            }
        }.parse();
    }
}
