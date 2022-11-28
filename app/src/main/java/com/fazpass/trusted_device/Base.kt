package com.fazpass.trusted_device

abstract class Base {
    var td_status: TRUSTED_DEVICE? = null
    var cd_status: CROSS_DEVICE? = null
    protected var ca_status: CROSS_APP? = null
    val DEBUG = "http://localhost:8080/"
    val STAGING = "https://channa.fazpas.com/"
    val BASE_URL = "base_url"
    val MERCHANT_TOKEN = "merchant_token"
    val PRODUCTION = "https://api.fazpass.com/"
    val PACKAGE_NAME = "package_name"
    val PRIVATE_KEY = "private_key"
    val PUBLIC_KEY = "public_key"
    val META = "meta"
    val USER_EMAIL = "user_email"
    val USER_PHONE = "user_phone"
    val USER_PIN = "user_pin"
    val USER_ID = "user_id"
    val DEVICE = "device"
    val LOGO = "logo"
    val FAZPASS = "com.fazpass.sdk"
    val TAG = "fazpass"
    val LOCAL_MISSING = "LOCAL DATA IS MISSING"
    val PIN_NOT_MATCH = "COMPARING PIN IS FAILED"
}