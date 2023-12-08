package com.example.project_game_punk.features.common

import android.os.Build
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun String.dateToUnix(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val localDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val string = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).time.toString()
        return  string.split("00").first() + "00"
    }
    return this
}



fun <T> List<T>.commaSeparated(collapse: (T) -> String): String {
    return joinToString(postfix = ",") { element ->
        collapse.invoke(element)
    }.let { joinedString ->
        joinedString.substring(0, joinedString.length - 1)
    }
}
