package com.deyvidandrades.noted.dataclasses

data class Categoria(
    var titulo: String,
    var numAnotacoes: Int,
    var numCaracteres: Int
) : Comparable<Categoria> {

    override operator fun compareTo(other: Categoria): Int {
        return numAnotacoes.compareTo(other.numAnotacoes)
    }
}