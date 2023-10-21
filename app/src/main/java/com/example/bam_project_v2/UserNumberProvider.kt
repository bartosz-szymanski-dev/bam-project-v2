package com.example.bam_project_v2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class UserNumberProvider : ContentProvider() {

    companion object {
        private const val USER_NUMBERS = 1
        private const val AUTHORITY = "com.example.bam_project_v2.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "usernumbers", USER_NUMBERS)
        }
    }

    private lateinit var db: AppDatabase

    override fun onCreate(): Boolean {
        db = AppDatabase.getDatabase(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER_NUMBERS -> {
                val dao = db.userNumberDao()
                val cursor = dao.getAllCursor()
                cursor.setNotificationUri(context!!.contentResolver, uri)
                cursor
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}