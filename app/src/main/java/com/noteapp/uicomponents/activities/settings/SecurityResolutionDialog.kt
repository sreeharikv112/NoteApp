package com.noteapp.uicomponents.activities.settings

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.noteapp.R
import android.R.attr.x
import android.graphics.Point
import android.view.*
import android.widget.TextView


class SecurityResolutionDialog(var question:String) : DialogFragment() {

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
        qstn.setText(question)

    }

    override fun onResume() {

        val window = dialog!!.window

        val size = Point()

        val display = window!!.windowManager.defaultDisplay

        display.getSize(size)

        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout(((size.x * 0.90).toInt()), WindowManager.LayoutParams.WRAP_CONTENT)

        window.setGravity(Gravity.CENTER)

        super.onResume()
    }

}