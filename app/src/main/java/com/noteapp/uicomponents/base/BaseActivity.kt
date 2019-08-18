package com.noteapp.uicomponents.base

import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.noteapp.R
import com.noteapp.common.AppLogger
import com.noteapp.common.AppUtils
import com.noteapp.uicomponents.activities.uiinterfaces.AlertCallBack

open class BaseActivity : AppCompatActivity(), AlertCallBack {

    private lateinit var mCallBackAlertDialog:AlertDialog
    protected val mAppLogger : AppLogger = AppLogger()
    protected val mAppUtils: AppUtils = AppUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showAlert(message :String,positiveBtnText: Int, negativeBtnText:Int){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)

        dialogBuilder.setPositiveButton(positiveBtnText) { dialog, whichButton -> handlePositiveAlertCallBack() }
        dialogBuilder.setNegativeButton(negativeBtnText) { dialog, whichButton -> handleNegativeAlertCallBack() }
        mCallBackAlertDialog = dialogBuilder.create()
        mCallBackAlertDialog.setCancelable(true)
        mCallBackAlertDialog.show()
    }

    fun showAlert(message :Int, positiveBtnText: Int, negativeBtnText:Int,
                  positiveListener: DialogInterface.OnClickListener,
                  negativeListener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.alert_title))
                .setMessage(message)
                .setPositiveButton(positiveBtnText, positiveListener)
                .setNegativeButton(negativeBtnText, negativeListener)
                .show()
    }


    fun showAlertCustomView(positiveBtnText: Int, negativeBtnText:Int,
                  positiveListener: DialogInterface.OnClickListener,
                  negativeListener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.alert_title))
                .setView(R.layout.content_security_resolution)
                .setPositiveButton(positiveBtnText, positiveListener)
                .setNegativeButton(negativeBtnText, negativeListener)
                .show()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun showToast(msg: String) {
            runOnUiThread {
                val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
    }

    override fun handleNegativeAlertCallBack() {
        mCallBackAlertDialog.dismiss()
    }

    override fun handlePositiveAlertCallBack() {
        mCallBackAlertDialog.dismiss()
    }
}
