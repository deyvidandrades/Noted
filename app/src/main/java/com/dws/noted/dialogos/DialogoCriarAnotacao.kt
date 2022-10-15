package com.dws.noted.dialogos

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.dws.noted.R
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.objetos.Anotacao
import com.dws.noted.persistencia.Persistencia
import java.util.*

class DialogoCriarAnotacao(private var index: Int) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.dialogo_criar_anotacao, container, false)

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCriarAnotacao = view.findViewById<ImageView>(R.id.btn_criar_anotacao)
        val etAnotacaoConteudo = view.findViewById<EditText>(R.id.et_anotacao_conteudo)

        btnCriarAnotacao.setOnClickListener { v ->
            AnimacaoBotao.animar(v)

            val ans: MatchResult? =
                "@[a-z0-9]\\S+".toRegex().find(etAnotacaoConteudo.text.toString(), 0)

            val titulo = if (ans?.groupValues?.get(0) != null) ans.groupValues[0] else ""

            if (etAnotacaoConteudo.text.length > 3) {

                Persistencia.getInstance(
                    dialog!!.context.getSharedPreferences(
                        "MAIN_DATA",
                        Context.MODE_PRIVATE
                    )
                ).adicionarAnotacao(
                    index, Anotacao(
                        titulo,
                        etAnotacaoConteudo.text.toString(),
                        Calendar.getInstance().timeInMillis
                    )
                )
            } else {
                Toast.makeText(
                    context,
                    "Titulo deve ter mais que 3 caracteres.",
                    Toast.LENGTH_LONG
                ).show()
            }
            dismiss()
        }

        return view
    }

    companion object {
        fun newInstance(index: Int): DialogoCriarAnotacao {
            return DialogoCriarAnotacao(index)
        }
    }
}