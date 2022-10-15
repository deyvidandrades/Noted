package com.dws.noted.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.dws.noted.adaptadores.AdaptadorDiretorios
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.databinding.ActivityMainBinding
import com.dws.noted.dialogos.DialogoCriarDiretorio
import com.dws.noted.objetos.Diretorio
import com.dws.noted.persistencia.Persistencia


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var persistencia: Persistencia

    private var arrayDiretorios = ArrayList<Diretorio>()
    private lateinit var adaptadorDiretorios: AdaptadorDiretorios

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        persistencia =
            Persistencia.getInstance(getSharedPreferences("MAIN_DATA", Context.MODE_PRIVATE))

        AppCompatDelegate.setDefaultNightMode(
            if (persistencia.getTemaEscuro())
                AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        adaptadorDiretorios = AdaptadorDiretorios(this, arrayDiretorios, this)

        binding.recyclerDiretorios.setHasFixedSize(true)
        binding.recyclerDiretorios.adapter = adaptadorDiretorios
        binding.recyclerDiretorios.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        binding.btnAdicionarDiretorio.setOnClickListener { v ->
            AnimacaoBotao.animar(v)

            val newFragment: DialogoCriarDiretorio = DialogoCriarDiretorio.newInstance()

            if (newFragment.isAdded) {
                newFragment.dismiss()
                val tsLong = System.currentTimeMillis() / 1000
                newFragment.show(supportFragmentManager, "atualizacao$tsLong")

            } else {
                val tsLong = System.currentTimeMillis() / 1000
                newFragment.show(supportFragmentManager, "atualizacao$tsLong")
            }
        }

        binding.btnConfig.setOnClickListener { v ->
            AnimacaoBotao.animar(v)

            val intent = Intent(this, ConfiguracoesActivity::class.java)
            startActivity(intent)
        }

        binding.swipeToRefresh.setOnRefreshListener {
            carregarDiretorios()
            binding.swipeToRefresh.isRefreshing = false
        }

        carregarDiretorios()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun carregarDiretorios() {
        arrayDiretorios.clear()
        arrayDiretorios.addAll(persistencia.getArrayDiretorios())
        adaptadorDiretorios.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        carregarDiretorios()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)
            carregarDiretorios()
    }
}