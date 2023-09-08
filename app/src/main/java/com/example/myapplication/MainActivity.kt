package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Marks Cimermanis", "Ieva Madara Glavecka", "Kristiāns Žondaks")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, name2: String, name3: String, modifier: Modifier = Modifier) {
    Text(
            text = "This is the first application from Group G-03-23:\n" +
                    "$name\n" +
                    "$name2\n" +
                    "$name3\n" +
                    "And the application was developed by $name",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Marks Cimermanis", "Ieva Glavecka", "Kristians Zondaks")
    }
}