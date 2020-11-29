package com.example.newsfeed.newsfeed.data

data class Item(
    val id: String,
    var title: String,
    var text: String,
    var date: String,
    var version: Int,
    var modified: Boolean
) {
    override fun toString(): String = text
}
