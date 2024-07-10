package com.dws.noted.adaptadores

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.dws.noted.R
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.dialogos.DialogoExibirAnotacao
import com.dws.noted.objetos.Anotacao
import com.dws.noted.persistencia.Persistencia
import java.util.*

class AdaptadorAnotacoes(
    private val context: Context,
    arrayList: ArrayList<Anotacao>,
    private val fragmentManager: FragmentManager,
    private val indexDiretorio: Int
) :
    RecyclerView.Adapter<AdaptadorAnotacoes.ViewHolder>() {

    private var arrayList: ArrayList<Anotacao> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_anotacao, parent, false
        )
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayList[position]

        holder.tvAnotacaoTitulo.visibility =
            if (item.getTitulo().equals("")) View.GONE
            else View.VISIBLE

        holder.tvAnotacaoTitulo.text = item.getTitulo()!!.replace("@", "")
        holder.tvAnotacaoConteudo.text = item.getConteudo()!!.replace(item.getTitulo()!!, "")

        holder.liAnotacao.setOnClickListener { v ->
            AnimacaoBotao.animar(v)

            val newFragment: DialogoExibirAnotacao =
                DialogoExibirAnotacao.newInstance(item)

            if (newFragment.isAdded) {
                newFragment.dismiss()
                val tsLong = System.currentTimeMillis() / 1000
                newFragment.show(fragmentManager, "atualizacao$tsLong")

            } else {
                val tsLong = System.currentTimeMillis() / 1000
                newFragment.show(fragmentManager, "atualizacao$tsLong")
            }
        }

        holder.liAnotacao.setOnLongClickListener { v ->
            AnimacaoBotao.animar(v)

            Persistencia.getInstance(
                context.getSharedPreferences(
                    "MAIN_DATA",
                    Context.MODE_PRIVATE
                )
            ).removerAnotacao(indexDiretorio, item)

            arrayList.remove(item)
            notifyItemRemoved(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAnotacaoConteudo: TextView = itemView.findViewById(R.id.tv_anotacao_conteudo)
        var tvAnotacaoTitulo: TextView = itemView.findViewById(R.id.tv_anotacao_titulo)
        var liAnotacao: LinearLayout = itemView.findViewById(R.id.item_anotacao)
    }

    init {
        this.arrayList = arrayList
    }
}