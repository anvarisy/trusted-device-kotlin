package com.fazpass.trusted_device

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class Storage {
    companion object {
        private val FAZPASS = "fazpass";
        private fun saveData(context: Context, key: String, value: String) {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun storeDataLocal(context: Context, key: String?, value: Int) {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun storeDataLocal(context: Context, key: String?, logo: Bitmap) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            logo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val b = byteArrayOutputStream.toByteArray()
            val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(key, encodedImage)
            editor.apply()
        }

        fun removeDataLocal(context: Context) {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
        }

        fun readDataLocal(context: Context, key: String?): String? {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            return sharedPref.getString(key, "")
        }

        fun readIntDataLocal(context: Context, key: String?): Int {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            return sharedPref.getInt(key, 0)
        }

        fun readBitmapLocal(context: Context, key: String?): Bitmap? {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            val b = Base64.decode(sharedPref.getString(key, ""), Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(b, 0, b.size)
        }

        private fun removeDataLocal(context: Context, key: String) {
            val sharedPref = context.getSharedPreferences(FAZPASS, Context.MODE_PRIVATE)
            sharedPref.edit().remove(key).apply()
        }

        fun storeDataLocal(context: Context, key: String, newValue: String) {
            if (isDataExists(context, key)) {
                removeDataLocal(context, key)
            }
            saveData(context, key, newValue)
        }

        private fun isDataExists(context: Context, key: String): Boolean {
            return readDataLocal(context, key) != ""
        }

        fun readDataPublic(
            context: Context,
            packageName: String?,
            password: String?,
            key: String?
        ): String? {
            return try {
                val packageContext = context.createPackageContext(packageName, 0)
                val pref = packageContext.getSharedPreferences(
                    password, Context.MODE_PRIVATE
                )
                pref.getString(key, "")
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                ""
            }
        }
    }
}