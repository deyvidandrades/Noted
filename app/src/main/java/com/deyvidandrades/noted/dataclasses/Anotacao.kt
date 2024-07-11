package com.deyvidandrades.noted.dataclasses

import java.util.Calendar


data class Anotacao(
    var conteudo: String,
    var categoria: String = "",
    val timestamp: Long = Calendar.getInstance().timeInMillis
) : Comparable<Anotacao> {

    override operator fun compareTo(other: Anotacao): Int {
        return timestamp.compareTo(other.timestamp)
    }
}