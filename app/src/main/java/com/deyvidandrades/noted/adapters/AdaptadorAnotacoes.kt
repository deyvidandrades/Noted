package com.deyvidandrades.noted.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.assistentes.DataUtil
import com.deyvidandrades.noted.dataclasses.Anotacao
import com.deyvidandrades.noted.interfaces.OnAdapterItemClickListener

class AdaptadorAnotacoes(
    private val context: Context,
    arrayList: ArrayList<Anotacao>,
    listener: OnAdapterItemClickListener
) :
    RecyclerView.Adapter<AdaptadorAnotacoes.ViewHolder>() {

    private val listener: OnAdapterItemClickListener
    private var arrayList: ArrayList<Anotacao> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_anotacao, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayList[position]

        holder.tvConteudo.text = item.conteudo
        holder.tvCategoria.text = "#${item.categoria}"
        holder.tvCategoria.visibility = if (item.categoria != "") View.VISIBLE else View.GONE

        holder.tvData.text = DataUtil.getDataFormatada(item.timestamp)

        holder.liItemHolder.setOnClickListener {
            listener.onItemClick(item)
        }

        holder.liItemHolder.setOnLongClickListener {
            listener.onItemLongClick(item)
            false
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var liItemHolder: LinearLayout = itemView.findViewById(R.id.li_item_holder)
        var tvCategoria: TextView = itemView.findViewById(R.id.tv_categoria)
        var tvConteudo: TextView = itemView.findViewById(R.id.tv_conteudo)
        var tvData: TextView = itemView.findViewById(R.id.tv_data)
    }

    init {
        this.arrayList = arrayList
        this.listener = listener
    }
}
