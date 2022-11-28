package com.fazpass.trusted_device

import android.content.Context
import com.framgia.android.emulator.EmulatorDetector

abstract class Fazpass : Base() {
    open fun initialize(context: Context, merchantToken: String?, mode: MODE?) {
        if (merchantToken == null || merchantToken == "") {
            throw NullPointerException("merchant id cannot be null or empty")
        }
        Storage.storeDataLocal(context, MERCHANT_TOKEN, merchantToken)
        Device(context)
        EmulatorDetector.with(context)
            .addPackageName("com.browserstack")
            .setDebug(true)
            .detect { isEmulator: Boolean ->
                if (isEmulator) {
                    throw SecurityException("Device rooted or is an emulator lib stage")
                }
            }
/*        SentryAndroid.init(context!!) { options: SentryAndroidOptions ->
            options.dsn =
                "https://1f85de8be5544aaab7847e377b4c6227@o1173329.ingest.sentry.io/6720667"
            options.tracesSampleRate = 1.0
        }*/
    }
}