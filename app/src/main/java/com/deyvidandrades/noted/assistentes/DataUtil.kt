package com.deyvidandrades.noted.assistentes

import java.text.SimpleDateFormat
import java.util.Locale

object DataUtil {

    fun getDataFormatada(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        return dateFormat.format(timestamp).replace(".", "")
    }
}