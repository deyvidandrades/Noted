package com.dws.noted.asistentes

import android.content.Context
import android.widget.Toast

abstract class DWS {
    companion object {
        fun getDados(dados: String) {
            println("DWS.D.$dados")
        }

        fun getToast(context: Context?, dados: String?, time: Int) {
            Toast.makeText(context, dados, time).show()
        }
    }
}