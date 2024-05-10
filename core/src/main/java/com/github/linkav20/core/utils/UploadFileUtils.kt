package com.github.linkav20.core.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private const val SERVER_FILE_NAME = "file"
suspend fun multipartBodyPartsFromUri(
    uri: Uri,
    contentResolver: ContentResolver,
    context: Context,
    fieldName: String = SERVER_FILE_NAME,
): MultipartBody.Part? {
    return withContext(Dispatchers.IO) {
        val builder = MultipartBody.Builder().apply { setType(MultipartBody.FORM) }
        val inputStream = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            val file = saveTempFile(context, inputStream, uri.getFileName(contentResolver) ?: "")
            if (file != null) {
                builder.addFormDataPart(
                    /*name =*/
                    fieldName,
                    /*filename =*/
                    uri.getFileName(contentResolver),
                    /*body =*/
                    file.asRequestBody(contentResolver.getType(uri).orEmpty().toMediaType())
                )
            }
            inputStream.close()
        }
        builder.build().parts.firstOrNull()
    }
}

suspend fun fileFromUri(
    uri: Uri,
    contentResolver: ContentResolver,
    context: Context,
): File? = withContext(Dispatchers.IO) {
    val inputStream = contentResolver.openInputStream(uri) ?: return@withContext null
    val file = saveTempFile(context, inputStream, uri.getFileName(contentResolver) ?: "")
    inputStream.close()
    file
}

fun Uri.getFileName(contentResolver: ContentResolver): String? = runCatching {
    contentResolver
        .query(this, null, null, null, null)
        ?.use { cursor ->
            cursor.moveToFirst()
            val fileName = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                .let(cursor::getString)
            cursor.close()
            return@use fileName
        }
}.getOrNull()
