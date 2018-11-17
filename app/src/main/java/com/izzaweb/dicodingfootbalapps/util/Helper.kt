package com.izzaweb.dicodingfootbalapps.util

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun String.dateFormat(inputFormat: String = "yyyy-MM-dd",
                         outputFormat: String = "E, MMM dd yyyy"): String {
    val dateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    val date = dateFormat.parse(this)

    val returnFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return returnFormat.format(date)
}

fun String.timeFormat(inputFormat: String = "HH:mm:ss",
                         outputFormat: String = "HH:mm"): String {

    val timeFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    val time = timeFormat.parse(this)

    val returnFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return returnFormat.format(time)
}

fun String?.nullToEmpty(): String = this ?: ""
fun String.SplitRapi(splitSeparator: String, joinSeparator: String): String {
    // Convert to list first (Split)
    val list: List<String> = this.split(splitSeparator).map { it.trim() }

    // Convert list to string (join)
    val output = list.joinToString(joinSeparator)
    return output
}