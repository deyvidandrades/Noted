package com.dws.noted.dialogos

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.dws.noted.R
import com.dws.noted.objetos.Anotacao

class DialogoExibirAnotacao(private var anotacao: Anotacao) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.dialogo_exibir_anotacao, container, false)

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val tvAnotacaoTitulo = view.findViewById<TextView>(R.id.tv_anotacao_titulo)
        val tvAnotacaoConteudo = view.findViewById<TextView>(R.id.tv_anotacao_conteudo)

        tvAnotacaoTitulo.visibility =
            if (anotacao.getTitulo().equals("")) View.GONE
            else View.VISIBLE

        tvAnotacaoTitulo.text = anotacao.getTitulo()!!.replace("@", "")
        tvAnotacaoConteudo.text = anotacao.getConteudo()!!.replace(anotacao.getTitulo()!!, "")

        return view
    }

    companion object {
        fun newInstance(anotacao: Anotacao): DialogoExibirAnotacao {
            return DialogoExibirAnotacao(anotacao)
        }
    }
}