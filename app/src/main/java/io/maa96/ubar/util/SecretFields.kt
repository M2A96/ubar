package io.maa96.ubar.util

import javax.inject.Inject

class SecretFields @Inject constructor() {
    private val debugBaseUrl = "https://stage.achareh.ir/api/karfarmas/"
    private val releaseBaseUrl = "https://stage.achareh.ir/api/karfarmas/"


    //    todo remember to use BuildConfig.DEBUG
    private val isDebug: Boolean = true
    fun getBaseUrl(): String {
        return if (isDebug) {
            debugBaseUrl
        } else {
            releaseBaseUrl
        }
    }
}