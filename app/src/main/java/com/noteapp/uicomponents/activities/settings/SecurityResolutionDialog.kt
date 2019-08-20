package com.noteapp.uicomponents.activities.settings

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.noteapp.R
import android.R.attr.x
import android.graphics.Point
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.noteapp.common.AppUtils
import kotlinx.android.synthetic.main.content_security.*
import kotlinx.android.synthetic.main.content_security_resolution.*


class SecurityResolutionDialog(var question:String,var answer: String, var listener: UserEnteredAnswer) : DialogFragment(), View.OnClickListener {

    val mAppUtils: AppUtils = AppUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.content_security_resolution, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val qstn = view.findViewById<TextView>(R.id.question)
        qstn.text = question
        val btnSubmit = view.findViewById<MaterialButton>(R.id.btnSubmit)
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnSubmit){
            if (!mAppUtils.isInputEditTextFilled(edtAnswer!!, answerLayout!!, getString(R.string.enter_security_answer))) {
                edtAnswer.requestFocus()
                return
            }
            else if( edtAnswer.text.toString()!!.equals(answer , true)){
                listener.userEnteredCorrectInput(true)
            }else{
                mAppUtils.showErrorInTextField(answerLayout!!, getString(R.string.wrong_answer))
            }
        }
    }

    override fun onResume() {
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout(((size.x * 0.95).toInt()), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }

    interface UserEnteredAnswer {
        fun userEnteredCorrectInput(status:Boolean)
    }

}