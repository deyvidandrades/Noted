package com.deyvidandrades.noted.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.adapters.AdaptadorAnotacoes
import com.deyvidandrades.noted.assistentes.Persistencia
import com.deyvidandrades.noted.dataclasses.Anotacao
import com.deyvidandrades.noted.interfaces.OnAdapterItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogoAnotacoesCategoria : BottomSheetDialogFragment(), OnAdapterItemClickListener {
    var categoria: String? = null

    private lateinit var tvTitulo: TextView
    private var arrayAnotacoes = ArrayList<Anotacao>()
    private lateinit var adaptadorAnotacoes: AdaptadorAnotacoes

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialogo_anotacoes_categoria, container, false)

        //Recycler Eventos
        val mySwipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        val recycler: RecyclerView = view.findViewById(R.id.recycler)

        tvTitulo = view.findViewById(R.id.tv_titulo)

        adaptadorAnotacoes = AdaptadorAnotacoes(requireContext(), arrayAnotacoes, this)

        recycler.setHasFixedSize(false)
        recycler.adapter = adaptadorAnotacoes
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mySwipeRefreshLayout.setOnRefreshListener {
            carregarAnotacoes()
            mySwipeRefreshLayout.isRefreshing = false
        }

        carregarAnotacoes()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carregarAnotacoes() {
        if (categoria != null) {
            tvTitulo.text = if (categoria != "") categoria else getString(R.string.default_)
            arrayAnotacoes.clear()
            arrayAnotacoes.addAll(Persistencia.getAnotacoesByCategoria(categoria!!))
            arrayAnotacoes.sortByDescending { it.timestamp }
            adaptadorAnotacoes.notifyDataSetChanged()
        }
    }

    override fun onItemClick(item: Any) {

    }

    override fun onItemLongClick(item: Any) {

    }
}