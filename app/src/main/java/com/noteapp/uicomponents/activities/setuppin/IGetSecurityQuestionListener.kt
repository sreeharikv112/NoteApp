package com.noteapp.uicomponents.activities.setuppin

import com.noteapp.models.SecurityQuestionModel
import com.noteapp.uicomponents.activities.settings.SettingsActivity

interface IGetSecurityQuestionListener {

    fun fetchSecurityQstnListener(securityQuestion : SecurityQuestionModel?, operation: SettingsActivity.OPERATION)
}