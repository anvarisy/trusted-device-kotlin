package com.fazpass.trusted_device

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.scottyab.rootbeer.RootBeer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.lang.Exception

class Device {
    private var context: Context? = null
    private var device: String? = null
    var notificationToken: String? = null

    constructor(context: Context) {
        this.context = context
        initialize(context)
        readNotificationToken().subscribe({ s: String? ->
            notificationToken = s
        }) { err: Throwable? -> }
    }


    private fun initialize(context: Context) {
        device = readMeta() + "-" + generateAppId(context)
    }

    @SuppressLint("HardwareIds")
    private fun generateAppId(context: Context): String {
        return Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    private fun readMeta(): String {
        return "" + Build.BRAND + "," + Build.MODEL + "," + Build.VERSION.SDK_INT
    }

    fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }

    fun isRooted(): Boolean {
        val rootBeer = RootBeer(context)
        return rootBeer.isRooted
    }

    fun getDevice(): String? {
        return device
    }


    private fun readNotificationToken(): Observable<String?> {
        return Observable.create { subscriber: ObservableEmitter<String?> ->
            FirebaseMessaging.getInstance().token.addOnSuccessListener { s: String? ->
                subscriber.onNext(s)
                subscriber.onComplete()
            }.addOnFailureListener { error: Exception? ->
                subscriber.onError(
                    error
                )
            }.addOnCompleteListener { a: Task<String?>? -> }
        }
    }
}