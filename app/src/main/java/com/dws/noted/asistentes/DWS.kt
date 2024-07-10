package com.dws.noted.asistentes

@Suppress("unused")
abstract class DWS {
    companion object {
        fun getDados(dados: String) {
            println("DWS.D.$dados")
        }
    }
}