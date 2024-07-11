package com.deyvidandrades.noted.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.adapters.AdaptadorAnotacoes
import com.deyvidandrades.noted.assistentes.Persistencia
import com.deyvidandrades.noted.dataclasses.Anotacao
import com.deyvidandrades.noted.dialogs.DialogoEditarAnotacao
import com.deyvidandrades.noted.interfaces.OnAdapterItemClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentAnotacoes : Fragment(R.layout.fragment_anotacoes), OnAdapterItemClickListener {
    private var arrayAnotacoes = ArrayList<Anotacao>()
    private lateinit var adaptadorAnotacoes: AdaptadorAnotacoes

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_anotacoes, container, false)

        val mySwipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        val btnAdicionarAnotacao: FloatingActionButton = view.findViewById(R.id.btn_adicionar)

        //Recycler Eventos
        val recycler: RecyclerView = view.findViewById(R.id.recycler)
        adaptadorAnotacoes = AdaptadorAnotacoes(requireContext(), arrayAnotacoes, this)

        recycler.setHasFixedSize(false)
        recycler.adapter = adaptadorAnotacoes
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mySwipeRefreshLayout.setOnRefreshListener {
            carregarAnotacoes()
            mySwipeRefreshLayout.isRefreshing = false
        }

        btnAdicionarAnotacao.setOnClickListener {
            val customBottomSheet = DialogoEditarAnotacao()
            customBottomSheet.show(parentFragmentManager, DialogoEditarAnotacao().javaClass.name)
        }

        carregarAnotacoes()
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun carregarAnotacoes() {
        arrayAnotacoes.clear()
        arrayAnotacoes.addAll(Persistencia.getAnotacoes())
        arrayAnotacoes.sortByDescending { it.timestamp }
        adaptadorAnotacoes.notifyDataSetChanged()
    }

    override fun onItemClick(item: Any) {
        val customBottomSheet = DialogoEditarAnotacao()
        customBottomSheet.anotacao = item as Anotacao
        customBottomSheet.show(parentFragmentManager, DialogoEditarAnotacao().javaClass.name)
    }

    override fun onItemLongClick(item: Any) {
        Persistencia.removerAnotacao((item as Anotacao).timestamp)
        carregarAnotacoes()
        Toast.makeText(requireContext(), getString(R.string.item_removido), Toast.LENGTH_SHORT).show()
    }
}