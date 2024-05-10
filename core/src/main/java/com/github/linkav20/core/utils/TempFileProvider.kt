package com.github.linkav20.core.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.function.Consumer


private const val BUFFER_SIZE = 8192

class TempFileProvider : FileProvider() {

    companion object {
        const val PATH_FOLDER_TEMP_FILES = "temp"
    }
}

fun printPdf(
    context: Context,
    uri: Uri
) = shareFile(
    context = context,
    uri = uri,
    fileAction = Intent.ACTION_VIEW,
    matchOnlyDefaultByMime = true,
    mimeType = "application/pdf"
)

fun File.appendAll(vararg files: File) {
    if (!exists()) {
        throw NoSuchFileException(this, null, "File doesn't exist.")
    }
    require(!isDirectory) { "The file is a directory." }
    FileOutputStream(this, true).use { output ->
        for (file in files) {
            if (file.isDirectory || !file.exists()) {
                continue // Might want to log or throw
            }
            file.forEachBlock(BUFFER_SIZE) { buffer, bytesRead -> output.write(buffer, 0, bytesRead) }
        }
    }
}

fun shareFile(
    context: Context,
    uri: Uri,
    fileAction: String = Intent.ACTION_SEND,
    matchOnlyDefaultByMime: Boolean = false,
    mimeType: String? = null,
) {
    assert(!(matchOnlyDefaultByMime && mimeType == null)) { "U need provide mime type for match it" }

    val shareIntent = Intent().apply {
        action = fileAction
        type = mimeType ?: "*/*"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    when (fileAction) {
        Intent.ACTION_VIEW -> shareIntent.setDataAndType(uri, mimeType)
        else -> shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    }

    val chooserIntent = Intent.createChooser(shareIntent, null)
    val resInfoList = context.packageManager.queryIntentActivities(
        chooserIntent,
        if (matchOnlyDefaultByMime && mimeType != null) PackageManager.MATCH_DEFAULT_ONLY else PackageManager.MATCH_ALL
    )
    for (resolveInfo in resInfoList) {
        val packageName = resolveInfo.activityInfo.packageName
        context.grantUriPermission(
            packageName, uri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }
    context.startActivity(chooserIntent)
}

suspend fun saveTempFileAndGetUri(context: Context, data: InputStream, extension: String): Uri? {
    val file = createTempFileAndOptionallyWriteToIt(context, data, extension)
    return file?.let {
        FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".${TempFileProvider.PATH_FOLDER_TEMP_FILES}",
            file,
        )
    }
}

suspend fun saveTempFile(context: Context, data: InputStream, extension: String): File? =
    createTempFileAndOptionallyWriteToIt(context, data, extension)

suspend fun createTempFileAndOptionallyWriteToIt(
    context: Context,
    data: InputStream?,
    extension: String
): File? = withContext(Dispatchers.IO) {
    try {
        val sharedFile = File.createTempFile(
            "tmp_",
            extension,
            File(context.cacheDir, TempFileProvider.PATH_FOLDER_TEMP_FILES).apply { mkdir() },
        )
        if (data != null) {
            val buf = ByteArray(BUFFER_SIZE)
            val fileOutputStream = FileOutputStream(sharedFile)

            var length: Int
            while (data.read(buf).also { length = it } != -1) {
                fileOutputStream.write(
                    buf,
                    0,
                    length
                )
            }
            fileOutputStream.close()
        }
        sharedFile
    } catch (e: IOException) {
        null
    }
}
