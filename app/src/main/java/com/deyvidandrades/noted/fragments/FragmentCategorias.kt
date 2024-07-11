package com.deyvidandrades.noted.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.adapters.AdaptadorCategorias
import com.deyvidandrades.noted.assistentes.Persistencia
import com.deyvidandrades.noted.dataclasses.Categoria
import com.deyvidandrades.noted.interfaces.OnAdapterItemClickListener

class FragmentCategorias : Fragment(R.layout.fragment_categorias), OnAdapterItemClickListener {
    private var arrayCategorias = ArrayList<Categoria>()
    private lateinit var adaptadorCategorias: AdaptadorCategorias

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        val mySwipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh)

        //Recycler Eventos
        val recycler: RecyclerView = view.findViewById(R.id.recycler)
        adaptadorCategorias = AdaptadorCategorias(requireContext(), arrayCategorias, this)

        recycler.setHasFixedSize(false)
        recycler.adapter = adaptadorCategorias
        recycler.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        mySwipeRefreshLayout.setOnRefreshListener {
            carregarAnotacoes()
            mySwipeRefreshLayout.isRefreshing = false
        }

        carregarAnotacoes()
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carregarAnotacoes() {
        arrayCategorias.clear()
        arrayCategorias.addAll(Persistencia.getCategorias())
        arrayCategorias.sortByDescending { it.numAnotacoes }
        adaptadorCategorias.notifyDataSetChanged()
    }

    override fun onItemClick(item: Any) {
        carregarAnotacoes()
    }

    override fun onItemLongClick(item: Any) {}
}