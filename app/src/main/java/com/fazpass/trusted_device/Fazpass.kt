package com.fazpass.trusted_device

import android.content.Context

abstract class Fazpass : Base() {
    companion object{
        open fun initialize(context: Context, merchantToken: String?, mode: MODE?) {
            if (merchantToken == null || merchantToken == "") {
                throw NullPointerException("merchant id cannot be null or empty")
            }
            Storage.storeDataLocal(context, "merchant_token", merchantToken)
//            Device(context)
/*        SentryAndroid.init(context!!) { options: SentryAndroidOptions ->
            options.dsn =
                "https://1f85de8be5544aaab7847e377b4c6227@o1173329.ingest.sentry.io/6720667"
            options.tracesSampleRate = 1.0
        }*/
        }
    }

}