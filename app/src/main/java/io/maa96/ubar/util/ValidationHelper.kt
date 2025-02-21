package io.maa96.ubar.util

interface ValidationHelper {
    fun validateStringNotEmpty(s: String): Boolean
    fun isPhoneNumberValid(phone: String): Boolean
    fun isEmailValid(email: String): Boolean
    fun isFirstNameValid(name: String): Boolean
    fun isLastNameValid(lastName: String): Boolean
}