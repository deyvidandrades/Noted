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
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import com.dws.noted.R
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.objetos.Diretorio
import com.dws.noted.persistencia.Persistencia

class DialogoCriarDiretorio : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.dialogo_criar_diretorio, container, false)

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val btnCriarDiretorio = view.findViewById<ImageView>(R.id.btn_criar_diretorio)
        val etDiretorioTitulo = view.findViewById<EditText>(R.id.et_diretorio_titulo)
        val swPrivado = view.findViewById<SwitchCompat>(R.id.sw_privado)

        btnCriarDiretorio.setOnClickListener { v ->
            AnimacaoBotao.animar(v)

            if (etDiretorioTitulo.text.length > 3) {

                val diretorio =
                    Diretorio(etDiretorioTitulo.text.toString(), ArrayList(), swPrivado.isChecked)

                Persistencia.getInstance(
                    dialog!!.context.getSharedPreferences(
                        "MAIN_DATA",
                        Context.MODE_PRIVATE
                    )
                ).adicionarDiretorio(diretorio)

                dialog!!.dismiss()
            } else {
                Toast.makeText(
                    context,
                    "Titulo deve ter mais que 3 caracteres.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return view
    }

    companion object {
        fun newInstance(): DialogoCriarDiretorio {
            return DialogoCriarDiretorio()
        }
    }
}