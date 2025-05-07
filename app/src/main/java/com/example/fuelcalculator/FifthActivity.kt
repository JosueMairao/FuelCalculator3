package com.example.fuelcalculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import java.util.Locale

class FifthActivity : AppCompatActivity() {
    private lateinit var txtResultado: TextView
    private lateinit var buttonFifth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_fifth)

        txtResultado = findViewById(R.id.txtResultado)
        buttonFifth = findViewById(R.id.buttonFifth)

        try {
            // Verificar que el intent no sea null
            if (intent == null) {
                Log.e("FifthActivity", "Error: Intent es null")
                Toast.makeText(this, "Error: No se recibieron datos", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            // Obtener los valores del intent
            val distancia = intent.getDoubleExtra("distancia", 0.0)
            val consumo = intent.getDoubleExtra("consumo", 0.0)
            val precio = intent.getDoubleExtra("precio", 0.0)

            Log.d("FifthActivity", "Valores recibidos - Distancia: $distancia, Consumo: $consumo, Precio: $precio")

            // Validar los valores
            if (distancia <= 0.0) {
                Log.e("FifthActivity", "Error: Distancia inválida: $distancia")
                Toast.makeText(this, "Error: La distancia debe ser mayor que 0", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            if (consumo <= 0.0) {
                Log.e("FifthActivity", "Error: Consumo inválido: $consumo")
                Toast.makeText(this, "Error: El consumo debe ser mayor que 0", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            if (precio <= 0.0) {
                Log.e("FifthActivity", "Error: Precio inválido: $precio")
                Toast.makeText(this, "Error: El precio debe ser mayor que 0", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            // Calcular el resultado
            val litrosNecesarios = (distancia * consumo) / 100
            val costoTotal = litrosNecesarios * precio

            // Mostrar el resultado
            val resultado = """
                Distancia: ${String.format(Locale.getDefault(), "%.2f", distancia)} km
                Consumo: ${String.format(Locale.getDefault(), "%.2f", consumo)} L/100km
                Precio: ${String.format(Locale.getDefault(), "%.2f", precio)} €/L
                
                Litros necesarios: ${String.format(Locale.getDefault(), "%.2f", litrosNecesarios)} L
                Costo total: ${String.format(Locale.getDefault(), "%.2f", costoTotal)} €
            """.trimIndent()

            txtResultado.text = resultado
            Log.d("FifthActivity", "Resultado mostrado: $resultado")

        } catch (e: Exception) {
            Log.e("FifthActivity", "Error inesperado: ${e.message}")
            Toast.makeText(this, "Error al procesar los datos", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Configurar el botón para volver al inicio
        buttonFifth.setOnClickListener {
            Log.d("FifthActivity", "Botón presionado - Volviendo al inicio")
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // No necesitamos limpiar las referencias de lateinit var
    }
}