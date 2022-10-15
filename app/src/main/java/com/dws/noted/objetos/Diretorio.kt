package com.dws.noted.objetos


data class Diretorio(
    private var nome: String?,
    private var arrayAnotacoes: ArrayList<Anotacao> = ArrayList(),
    private var privado: Boolean
) : Comparable<Diretorio> {

    fun getNome(): String = nome!!
    fun getAnotacoes(): ArrayList<Anotacao> = arrayAnotacoes
    fun getPrivado(): Boolean = privado
    fun getNumAnotacoes(): Int = arrayAnotacoes.size

    fun getNumPalavras(): Int {
        var num = 0
        for (item in arrayAnotacoes) {
            num += item.getConteudo()!!.split(" ").size
        }

        return num
    }

    fun getNumLinhas(): Int {
        var num = 0
        for (item in arrayAnotacoes) {
            num += item.getConteudo()!!.split("\n").size - 1
        }

        return num
    }

    fun adicionarAnotacao(anotacao: Anotacao) {
        arrayAnotacoes.add(anotacao)
    }

    fun atualizarAnotacao(indexAnotacao: Int, novaAnotacao: Anotacao) {
        arrayAnotacoes[indexAnotacao] = novaAnotacao
    }

    fun removerAnotacao(anotacao: Anotacao) {
        arrayAnotacoes.remove(anotacao)
    }

    override operator fun compareTo(other: Diretorio): Int {
        return getNome().compareTo(other.getNome())
    }

    override fun toString(): String {
        return "Diretorios(nome=$nome, arrayAnotacoes=$arrayAnotacoes)"
    }

}