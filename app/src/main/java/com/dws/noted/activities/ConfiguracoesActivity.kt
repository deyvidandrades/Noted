package com.dws.noted.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dws.noted.BuildConfig
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.databinding.ActivityConfiguracoesBinding
import com.dws.noted.persistencia.Persistencia

class ConfiguracoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracoesBinding
    private lateinit var persistencia: Persistencia

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguracoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        persistencia = Persistencia.getInstance(
            getSharedPreferences("MAIN_DATA", Context.MODE_PRIVATE)
        )

        val versionName: String = BuildConfig.VERSION_NAME

        binding.tvVersao.text = "v$versionName"

        binding.swTemaEscuro.isChecked = persistencia.getTemaEscuro()

        binding.swTemaEscuro.setOnCheckedChangeListener { _, isChecked ->
            persistencia.setTemaEscuro(isChecked)

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.btnVoltar.setOnClickListener { v ->
            AnimacaoBotao.animar(v)
            finish()
        }
    }
}