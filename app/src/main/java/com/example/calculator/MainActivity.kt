package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = result ?: "Sonuç", style = MaterialTheme.typography.headlineMedium)
        NumberInputField(label = "İlk Sayı", value = firstNumber) { firstNumber = it }
        Spacer(modifier = Modifier.height(8.dp))
        NumberInputField(label = "İkinci Sayı", value = secondNumber) { secondNumber = it }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("+", "-", "*", "/").forEach { operation ->
                Button(onClick = {
                    val num1 = firstNumber.toDoubleOrNull()
                    val num2 = secondNumber.toDoubleOrNull()

                    if (num1 == null || num2 == null) {
                        result = "Hata: Null değer girildi"
                        return@Button
                    }

                    result = when (operation) {
                        "+" -> (num1 + num2).toString()
                        "-" -> (num1 - num2).toString()
                        "*" -> (num1 * num2).toString()
                        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Tanımsız"
                        else -> "Hatalı işlem"
                    }
                }) {
                    Text(operation)
                }
            }
        }
    }
}

@Composable
fun NumberInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Calculator()
    }
}
