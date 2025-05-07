package com.example.fuelcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class SecondActivity : AppCompatActivity() {
    private lateinit var edtPrecio: EditText
    private lateinit var buttonSecond: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_second)

        edtPrecio = findViewById(R.id.edtPrecio)
        buttonSecond = findViewById(R.id.buttonsecond)

        buttonSecond.setOnClickListener {
            val precioStr = edtPrecio.text.toString().trim()
            
            if (precioStr.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce el precio del combustible", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            try {
                val precioStrFormatted = precioStr.replace(",", ".")
                    .replace("[^0-9.]".toRegex(), "")
                
                Log.d("SecondActivity", "Texto original: $precioStr, Texto formateado: $precioStrFormatted")
                
                if (precioStrFormatted.isEmpty()) {
                    Toast.makeText(this, "Por favor, introduce solo números (ejemplo: 1.5)", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                val precio = precioStrFormatted.toFloat()
                Log.d("SecondActivity", "Precio convertido a float: $precio")

                if (precio <= 0f) {
                    Toast.makeText(this, "El precio debe ser mayor que 0", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                val intent = Intent(this, ThirdActivity::class.java).apply {
                    putExtra("precio", precio)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                
                Log.d("SecondActivity", "Pasando a ThirdActivity - Precio: $precio")
                Log.d("SecondActivity", "Intent extras: ${intent.extras?.keySet()?.joinToString()}")
                
                startActivity(intent)
                finish() // Cerrar esta actividad después de iniciar la siguiente
            } catch (e: NumberFormatException) {
                Log.e("SecondActivity", "Error al convertir el precio: ${e.message}")
                Toast.makeText(this, "Por favor, introduce un número válido (ejemplo: 1.5)", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // No necesitamos limpiar las referencias de lateinit var
    }
}
