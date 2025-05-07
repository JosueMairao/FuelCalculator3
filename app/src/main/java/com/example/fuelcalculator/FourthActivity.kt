package com.example.fuelcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FourthActivity : AppCompatActivity() {
    private lateinit var edtConsumo: EditText
    private lateinit var buttonFourth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fourth)

        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtConsumo = findViewById(R.id.edtConsumo)
        buttonFourth = findViewById(R.id.buttonfourth)

        val precio = intent.getFloatExtra("precio", 0f)
        val distancia = intent.getFloatExtra("distancia", 0f)

        buttonFourth.setOnClickListener {
            val consumoText = edtConsumo.text.toString()

            if (consumoText.isNotEmpty()) {
                val consumo = consumoText.toFloatOrNull()

                if (consumo != null && consumo > 0f) {
                    val intent = Intent(this, FifthActivity::class.java)
                    intent.putExtra("distancia", distancia.toDouble())
                    intent.putExtra("consumo", consumo.toDouble())
                    intent.putExtra("precio", precio.toDouble())
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.enter_valid_consumption), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.enter_valid_consumption), Toast.LENGTH_SHORT).show()
            }
        }
    }
} 