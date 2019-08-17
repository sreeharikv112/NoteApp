package com.noteapp.uicomponents.activities.setuppin

import com.noteapp.models.SecurityQuestionModel

interface IGetSecurityQuestionListener {

    fun fetchSecurityQstnListener(securityQuestion : SecurityQuestionModel?)
}