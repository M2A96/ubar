package io.maa96.ubar.util

import javax.inject.Inject

class SecretFields @Inject constructor() {
    private val debugBaseUrl = "https://stage.achareh.ir/api/karfarmas/"
    private val releaseBaseUrl = "https://stage.achareh.ir/api/karfarmas/"
    private val username = "09822222222"
    private val password = "Sana12345678"


    //    todo remember to use BuildConfig.DEBUG
    private val isDebug: Boolean = true
    fun getBaseUrl(): String {
        return if (isDebug) {
            debugBaseUrl
        } else {
            releaseBaseUrl
        }
    }

    fun getPassword() = password
    fun getUsername() = username
}