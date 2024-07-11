package com.deyvidandrades.noted.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.assistentes.DataUtil
import com.deyvidandrades.noted.assistentes.Persistencia
import com.deyvidandrades.noted.dataclasses.Anotacao
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class DialogoEditarAnotacao : BottomSheetDialogFragment() {
    var anotacao: Anotacao? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dialogoView = inflater.inflate(R.layout.dialogo_editar_anotacao, container, false)

        val etEditarAnotacao: TextInputEditText = dialogoView.findViewById(R.id.et_editar_anotacao)
        val etCategoriaAnotacao: TextInputEditText = dialogoView.findViewById(R.id.et_categoria_anotacao)
        val btnSalvar: Button = dialogoView.findViewById(R.id.btn_salvar)
        val tvData: TextView = dialogoView.findViewById(R.id.tv_data)

        if (anotacao != null) {
            etEditarAnotacao.setText(anotacao!!.conteudo)
            tvData.text = DataUtil.getDataFormatada(anotacao!!.timestamp)
        }

        btnSalvar.setOnClickListener {
            if (etEditarAnotacao.text.toString() != "") {
                if (anotacao == null) {
                    Persistencia.adicionarAnotacao(
                        Anotacao(etEditarAnotacao.text.toString(), etCategoriaAnotacao.text.toString())
                    )
                } else {
                    Persistencia.changeAnotacaoConteudo(anotacao!!.timestamp, etEditarAnotacao.text.toString())
                    Persistencia.changeAnotacaoCategoria(anotacao!!.timestamp, etCategoriaAnotacao.text.toString())
                }
                dismiss()
            }
        }

        return dialogoView
    }
}