package com.deyvidandrades.noted.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.dataclasses.Categoria
import com.deyvidandrades.noted.dialogs.DialogoAnotacoesCategoria
import com.deyvidandrades.noted.interfaces.OnAdapterItemClickListener


class AdaptadorCategorias(
    private val context: Context,
    arrayList: ArrayList<Categoria>,
    listener: OnAdapterItemClickListener
) :
    RecyclerView.Adapter<AdaptadorCategorias.ViewHolder>() {
    private var arrayList: ArrayList<Categoria> = ArrayList()
    private val listener: OnAdapterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayList[position]

        holder.tvTitulo.text = item.titulo.ifEmpty { context.getString(R.string.default_) }
        holder.tvNumAnotacoes.text =
            context.getString(
                R.string.anotacoes_e_caracteres,
                item.numAnotacoes.toString(),
                item.numCaracteres.toString()
            )

        holder.liItemHolder.setOnClickListener {
            val customBottomSheet = DialogoAnotacoesCategoria()
            customBottomSheet.categoria = item.titulo
            customBottomSheet.show(
                (context as AppCompatActivity).supportFragmentManager,
                DialogoAnotacoesCategoria().javaClass.name
            )
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitulo: TextView = itemView.findViewById(R.id.tv_titulo)
        var tvNumAnotacoes: TextView = itemView.findViewById(R.id.tv_num_anotacoes)
        var liItemHolder: LinearLayout = itemView.findViewById(R.id.li_item_holder)
    }

    init {
        this.arrayList = arrayList
        this.listener = listener
    }
}
