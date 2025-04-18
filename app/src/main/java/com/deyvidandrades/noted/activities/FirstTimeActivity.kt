package com.deyvidandrades.noted.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.assistentes.Persistencia

class FirstTimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first_time)

        Persistencia.getInstance(this)
        val btnContinuar: Button = findViewById(R.id.btn_continuar)
        val tvTermos: TextView = findViewById(R.id.tv_termos)

        btnContinuar.setOnClickListener {
            Persistencia.setFirstTime()
            startActivity(Intent(this, MainActivity::class.java))
        }

        tvTermos.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, getString(R.string.url_termos).toUri()))
        }
    }
}