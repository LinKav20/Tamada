package com.github.linkav20.core.utils

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeUtils {
    private val formatWithoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private val formatWithZone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
    private val stringFormat = DateTimeFormatter.ofPattern("dd MMMM yy HH:mm")
    private val dateFormat = DateTimeFormatter.ofPattern("dd MMMM")
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun fromString(date: String): OffsetDateTime? {
        return try {
            OffsetDateTime.parse(date, formatWithZone)
        } catch (_: Exception) {
            null
        }
    }

    fun toString(value: OffsetDateTime): String = value.format(formatWithZone)

    fun toUiString(value: OffsetDateTime): String = value.format(stringFormat)

    fun toUiString(start: OffsetDateTime, end: OffsetDateTime): String =
        if (start.dayOfYear == end.dayOfYear) {
            val dateFormat = start.format(dateFormat)
            val startTime = start.format(timeFormat)
            val endTime = end.format(timeFormat)
            "$dateFormat   $startTime - $endTime"
        } else {
            "${toUiString(start)} - ${toUiString(end)}"
        }
}