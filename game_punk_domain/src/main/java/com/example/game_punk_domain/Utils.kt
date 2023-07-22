package com.example.game_punk_domain

fun <T> MutableList<T>.update(element: T) {
    val index = indexOf(element)
    set(index, element)
}