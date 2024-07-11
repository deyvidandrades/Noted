package com.deyvidandrades.noted.assistentes

import android.content.Context
import android.content.SharedPreferences
import com.deyvidandrades.noted.dataclasses.Anotacao
import com.deyvidandrades.noted.dataclasses.Categoria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Persistencia {
    var isFirstTime: Boolean = true
    var isDarkTheme: Boolean = false
    var isPrivado: Boolean = false
    var auth: Boolean = false

    private var preferences: SharedPreferences? = null
    private var arrayAnotacoes = ArrayList<Anotacao>()

    enum class Paths { ANOTACOES, IS_FIRST_TIME, IS_DARK_THEME, PRIVADO }

    fun getInstance(context: Context) {
        preferences = context.getSharedPreferences("MAIN_DATA", Context.MODE_PRIVATE)
        carregarDados()
    }

    /*FLUXO DADOS*/

    private fun carregarDados() {
        if (preferences != null) {
            val listaDiretorio = preferences!!.getString(Paths.ANOTACOES.name.lowercase(), "")!!

            isPrivado = preferences!!.getBoolean(Paths.PRIVADO.name.lowercase(), false)
            isFirstTime = preferences!!.getBoolean(Paths.IS_FIRST_TIME.name.lowercase(), true)
            isDarkTheme = preferences!!.getBoolean(Paths.IS_DARK_THEME.name.lowercase(), false)

            val typeTokenDiretorios = object : TypeToken<ArrayList<Anotacao>>() {}.type

            try {
                arrayAnotacoes.clear()
                arrayAnotacoes.addAll(Gson().fromJson(listaDiretorio, typeTokenDiretorios))
            } catch (e: NullPointerException) {
                arrayAnotacoes = ArrayList()
            }
        }
    }

    private fun salvarDados() {
        if (preferences != null) {
            with(preferences!!.edit()) {
                putBoolean(Paths.IS_FIRST_TIME.name.lowercase(), isFirstTime)
                putBoolean(Paths.IS_DARK_THEME.name.lowercase(), isDarkTheme)
                putBoolean(Paths.PRIVADO.name.lowercase(), isPrivado)

                putString(Paths.ANOTACOES.name.lowercase(), Gson().toJson(arrayAnotacoes))

                commit()
            }

            carregarDados()
        }
    }

    /*FLUXO SETTINGS*/
    fun setFirstTime() {
        isFirstTime = false
        salvarDados()
    }

    fun setDarkMode(value: Boolean) {
        isDarkTheme = value
        salvarDados()
    }

    fun setAnotacoesPrivado(value: Boolean) {
        isPrivado = value
        salvarDados()
    }

    /*FLUXO ANOTACOES*/

    fun getAnotacoes() = if (isPrivado && !auth) ArrayList() else arrayAnotacoes

    fun getAnotacoesByCategoria(categoria: String): List<Anotacao> {
        return arrayAnotacoes.filter { a -> a.categoria == categoria }
    }

    fun getCategorias(): ArrayList<Categoria> {
        val array = ArrayList<Categoria>()

        for (item in arrayAnotacoes) {
            var index = -1
            for (categoria in array)
                if (item.categoria == categoria.titulo)
                    index += 1

            if (index != -1) {
                array[index].numAnotacoes += 1
                array[index].numCaracteres += item.conteudo.length
            } else
                array.add(Categoria(item.categoria, 1, item.conteudo.length))
        }

        if (isPrivado && !auth) array.clear()
        return array
    }

    fun adicionarAnotacao(anotacao: Anotacao) {
        var bo = false
        for (item in arrayAnotacoes)
            if (item.timestamp == anotacao.timestamp) {
                bo = true
            }

        if (!bo) {
            arrayAnotacoes.add(anotacao)
            salvarDados()
        }
    }

    fun removerAnotacao(anotacaoId: Long) {
        arrayAnotacoes.removeIf { a ->
            a.timestamp == anotacaoId
        }
        salvarDados()
    }

    fun changeAnotacaoConteudo(anotacaoId: Long, conteudo: String) {
        for (anotacao in arrayAnotacoes) {
            if (anotacao.timestamp == anotacaoId) {
                anotacao.conteudo = conteudo
            }
        }
        salvarDados()
    }

    fun changeAnotacaoCategoria(anotacaoId: Long, categoria: String) {
        for (anotacao in arrayAnotacoes) {
            if (anotacao.timestamp == anotacaoId) {
                anotacao.categoria = categoria
            }
        }
        salvarDados()
    }
}