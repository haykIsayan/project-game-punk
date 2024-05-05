package com.example.project_game_punk.features.common

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Period
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

fun String.isSimilarTo(other: String): Boolean {


//    this.forEachIndexed { index, c ->
//
//    }


    return true
}

fun String.dateToMillis(): Long {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val date =  SimpleDateFormat("MMM dd, yyyy").parse(this)
        return date.time
    }
    return 0L
}

fun Long.till(milliSecond: Long): Period? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val from = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val to = Instant.ofEpochMilli(milliSecond)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        Period.between(from, to)
    } else {
        null
    }
}

fun Long.percentLeft(milliSecond: Long): Float {
    return toFloat() / milliSecond.toFloat() * 100f
}




fun <T> List<T>.commaSeparated(collapse: (T) -> String): String {
    return joinToString(postfix = ",") { element ->
        collapse.invoke(element)
    }.let { joinedString ->
        joinedString.substring(0, joinedString.length - 1)
    }
}
