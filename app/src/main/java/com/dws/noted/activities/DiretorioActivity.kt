package com.dws.noted.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dws.noted.adaptadores.AdaptadorAnotacoes
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.databinding.ActivityDiretorioBinding
import com.dws.noted.dialogos.DialogoCriarAnotacao
import com.dws.noted.objetos.Anotacao
import com.dws.noted.persistencia.Persistencia

class DiretorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiretorioBinding
    private lateinit var persistencia: Persistencia

    private var arrayAnotacoes = ArrayList<Anotacao>()
    private lateinit var adaptadorAnotacoes: AdaptadorAnotacoes
    private var index: Int = -1

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiretorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        index = intent.extras!!.getInt("index", 0)

        persistencia = Persistencia.getInstance(
            getSharedPreferences("MAIN_DATA", Context.MODE_PRIVATE)
        )

        adaptadorAnotacoes = AdaptadorAnotacoes(this, arrayAnotacoes, supportFragmentManager, index)

        binding.recyclerAnotacoes.setHasFixedSize(true)
        binding.recyclerAnotacoes.adapter = adaptadorAnotacoes
        binding.recyclerAnotacoes.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )

        binding.btnVoltar.setOnClickListener { v ->
            AnimacaoBotao.animar(v)
            finish()
        }
        binding.btnAdicionarAnotacao.setOnClickListener { v ->
            AnimacaoBotao.animar(v)

            val newFragment: DialogoCriarAnotacao = DialogoCriarAnotacao.newInstance(index)

            if (newFragment.isAdded) {
                newFragment.dismiss()
                val tsLong = System.currentTimeMillis() / 1000
                newFragment.show(supportFragmentManager, "atualizacao$tsLong")

            } else {
                val tsLong = System.currentTimeMillis() / 1000
                newFragment.show(supportFragmentManager, "atualizacao$tsLong")
            }
        }

        binding.swipeToRefresh.setOnRefreshListener {
            carregarAnotacoes(index)
            binding.swipeToRefresh.isRefreshing = false
        }

        carregarAnotacoes(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun carregarAnotacoes(index: Int) {
        val diretorio = persistencia.getDiretorio(index)

        binding.tvDiretorioTitulo.text = diretorio.getNome()

        arrayAnotacoes.clear()
        arrayAnotacoes.addAll(diretorio.getAnotacoes())
        arrayAnotacoes.sort()
        adaptadorAnotacoes.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        carregarAnotacoes(index)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)
            carregarAnotacoes(index)
    }
}