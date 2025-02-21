package io.maa96.ubar.util

import android.util.Patterns
import javax.inject.Inject

class Validator @Inject constructor() : ValidationHelper {
    override fun validateStringNotEmpty(s: String): Boolean {
        return s.isNotEmpty()
    }

    override fun isPhoneNumberValid(phone: String): Boolean {
        return phone.length == 11
                && phone.startsWith('0')
                && Patterns.PHONE.matcher(phone).matches()
    }

    override fun isEmailValid(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isFirstNameValid(name: String): Boolean {
        return name.length >= 5
    }

    override fun isLastNameValid(lastName: String): Boolean {
        return lastName.length >= 3
    }
}