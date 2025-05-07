package com.example.fuelcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class ThirdActivity : AppCompatActivity() {
    private lateinit var edtDistancia: EditText
    private lateinit var buttonThird: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_third)

        edtDistancia = findViewById(R.id.edtDistancia)
        buttonThird = findViewById(R.id.buttonthird)

        // Obtener el precio de la actividad anterior
        val precio = intent.getFloatExtra("precio", 0f)
        Log.d("ThirdActivity", "Precio recibido: $precio")

        if (precio <= 0f) {
            Log.e("ThirdActivity", "Error: Precio inválido recibido: $precio")
            Toast.makeText(this, "Error: El precio no es válido", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        buttonThird.setOnClickListener {
            val distanciaStr = edtDistancia.text.toString().trim()
            
            if (distanciaStr.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce la distancia del viaje", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            try {
                // Extraer solo los números del texto
                val distanciaStrFormatted = distanciaStr.replace(",", ".")
                    .replace("[^0-9.]".toRegex(), "")
                
                Log.d("ThirdActivity", "Texto original: $distanciaStr, Texto formateado: $distanciaStrFormatted")
                
                if (distanciaStrFormatted.isEmpty()) {
                    Toast.makeText(this, "Por favor, introduce solo números (ejemplo: 200)", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                val distancia = distanciaStrFormatted.toFloat()
                Log.d("ThirdActivity", "Distancia convertida a float: $distancia")

                if (distancia <= 0f) {
                    Toast.makeText(this, "La distancia debe ser mayor que 0", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                // Verificar que la distancia es un número válido
                if (distancia.isNaN() || distancia.isInfinite()) {
                    Toast.makeText(this, "La distancia debe ser un número válido", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                val intent = Intent(this, FourthActivity::class.java).apply {
                    putExtra("precio", precio)
                    putExtra("distancia", distancia)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                
                Log.d("ThirdActivity", "Pasando a FourthActivity - Precio: $precio, Distancia: $distancia")
                Log.d("ThirdActivity", "Intent extras: ${intent.extras?.keySet()?.joinToString()}")
                
                startActivity(intent)
                finish() // Cerrar esta actividad después de iniciar la siguiente
            } catch (e: NumberFormatException) {
                Log.e("ThirdActivity", "Error al convertir la distancia: ${e.message}")
                Toast.makeText(this, "Por favor, introduce un número válido (ejemplo: 100 o 100.5)", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e("ThirdActivity", "Error inesperado: ${e.message}")
                Toast.makeText(this, "Error al procesar la distancia. Por favor, intenta nuevamente.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // No necesitamos limpiar las referencias de lateinit var
    }
}
