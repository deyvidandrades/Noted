package com.dws.noted.persistencia

import android.content.SharedPreferences
import com.dws.noted.objetos.Anotacao
import com.dws.noted.objetos.Diretorio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Persistencia private constructor(private val preferences: SharedPreferences) {
    private var arrayDiretorios = ArrayList<Diretorio>()
    private var temaEscuro: Boolean = false

    init {
        carregarDados()
    }

    companion object {
        private var instance: Persistencia? = null

        fun getInstance(preferences: SharedPreferences): Persistencia {
            if (instance == null)
                instance = Persistencia(preferences)

            return instance!!
        }
    }

    private fun carregarDados() {
        try {
            val listaRaw = preferences.getString("diretorios", "")!!
            val typeToken = object : TypeToken<ArrayList<Diretorio>>() {}.type

            arrayDiretorios.clear()
            arrayDiretorios.addAll(Gson().fromJson(listaRaw, typeToken))
        } catch (e: NullPointerException) {
            arrayDiretorios = ArrayList()
        }
        temaEscuro = preferences.getBoolean("tema_escuro", false)
    }

    private fun salvarDados() {
        with(preferences.edit()) {
            putString("diretorios", Gson().toJson(arrayDiretorios))
            putBoolean("tema_escuro", temaEscuro)
            commit()
        }
    }

    private fun atualizarDados() {
        arrayDiretorios.sort()
        salvarDados()
        carregarDados()
    }

    //ALTERAÇÕES

    fun adicionarDiretorio(diretorio: Diretorio) {
        if (!arrayDiretorios.contains(diretorio))
            arrayDiretorios.add(diretorio)

        atualizarDados()
    }

    fun adicionarAnotacao(index: Int, anotacao: Anotacao) {
        arrayDiretorios[index].adicionarAnotacao(anotacao)
        atualizarDados()
    }

    fun removerDiretorio(diretorio: Diretorio) {
        arrayDiretorios.remove(diretorio)
        atualizarDados()
    }

    fun atualizarAnotacao(indexDiretorio: Int, indexAnotacao: Int, anotacao: Anotacao) {
        arrayDiretorios[indexDiretorio].atualizarAnotacao(indexAnotacao, anotacao)
        atualizarDados()
    }

    fun removerAnotacao(indexDiretorio: Int, anotacao: Anotacao) {
        arrayDiretorios[indexDiretorio].removerAnotacao(anotacao)
        atualizarDados()
    }

    //GETS

    fun getArrayDiretorios(): ArrayList<Diretorio> {
        atualizarDados()
        return arrayDiretorios
    }

    fun getDiretorio(index: Int): Diretorio {
        atualizarDados()
        return arrayDiretorios[index]
    }

    fun getTemaEscuro() = temaEscuro

    fun setTemaEscuro(boolean: Boolean) {
        temaEscuro = boolean
        atualizarDados()
    }
}