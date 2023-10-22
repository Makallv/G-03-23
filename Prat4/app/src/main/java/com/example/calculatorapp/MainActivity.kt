package com.example.calculatorapp

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var currentValue = ""
    private var storedValue = 0.0
    private var currentOperator = ""
    private var memoryValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize your views here if needed.
    }

    fun onNumberClick(view: View) {
        if (view is Button) {
            val number = view.text.toString()
            currentValue += number
            val display = findViewById<EditText>(R.id.display)
            display.append(number)
        }
    }

    fun onOperatorClick(view: View) {
        if (view is Button) {
            if (currentValue.isNotEmpty()) {
                storedValue = currentValue.toDouble()
                currentOperator = view.tag.toString()
                currentValue = ""
                val display = findViewById<EditText>(R.id.display)
                display.text.clear()
            }
        }
    }

    fun onEqualsClick(view: View) {
        if (currentValue.isNotEmpty()) {
            val num = currentValue.toDouble()
            when (currentOperator) {
                "+" -> storedValue += num
                "-" -> storedValue -= num
                "*" -> storedValue *= num
                "/" -> {
                    if (num != 0.0) {
                        storedValue /= num
                    } else {
                        // Handle division by zero error
                    }
                }
            }
            currentValue = storedValue.toString()
            val display = findViewById<EditText>(R.id.display)
            val editableText = Editable.Factory.getInstance().newEditable(currentValue)
            display.text = editableText
        }
    }

    fun onDecimalClick(view: View) {
        if (view is Button) {
            if (!currentValue.contains(".")) {
                // Ensure there is only one decimal point in the current value
                currentValue += view.text.toString()
                val display = findViewById<EditText>(R.id.display)
                display.append(view.text)
            }
        }
    }


    fun onMemorySaveClick(view: View) {
        if (currentValue.isNotEmpty()) {
            memoryValue = currentValue.toDouble()
        }
    }

    fun onMemoryReadClick(view: View) {
        currentValue = memoryValue.toString()
        val display = findViewById<EditText>(R.id.display)
        val editableText = Editable.Factory.getInstance().newEditable(currentValue)
        display.text = editableText
    }

    fun onMemoryClearClick(view: View) {
        val display = findViewById<EditText>(R.id.display)
        display.text.clear()
    }
}
