package com.dws.noted.objetos


data class Anotacao(
    private var titulo: String?,
    private var conteudo: String?,
    private var timestamp: Long?
) : Comparable<Anotacao> {

    fun getTitulo() = titulo
    fun getConteudo() = conteudo
    fun getTimestamp() = timestamp

    override operator fun compareTo(other: Anotacao): Int {
        return getTimestamp()!!.compareTo(other.getTimestamp()!!)
    }

    override fun toString(): String {
        return "Anotacoes(titulo=$titulo, conteudo=$conteudo, timestamp=$timestamp)"
    }
}