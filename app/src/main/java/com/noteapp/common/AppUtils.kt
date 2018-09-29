package com.noteapp.common

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AppUtils {

    fun isInputEditTextFilled(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            textInputLayout.error = message
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return true
    }
}