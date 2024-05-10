package com.github.linkav20.core.utils

import android.content.Context
import android.net.Uri

enum class FileType { IMAGE, DOCUMENT, VIDEO }

fun Uri.getFileType(
    context: Context
): FileType {
    val contentResolver = context.contentResolver
    val typeStr = contentResolver.getType(this)
    return when {
        typeStr?.startsWith("image", ignoreCase = true) == true -> FileType.IMAGE
        typeStr?.startsWith("video", ignoreCase = true) == true -> FileType.VIDEO
        else -> FileType.DOCUMENT
    }
}
