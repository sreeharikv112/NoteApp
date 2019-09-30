package com.noteapp.common

import android.os.Build
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

    fun showErrorInTextField(textInputLayout: TextInputLayout, message: String){
        if(!message.isEmpty()){
            textInputLayout.error = message
        }
    }
    fun isMarshMallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}