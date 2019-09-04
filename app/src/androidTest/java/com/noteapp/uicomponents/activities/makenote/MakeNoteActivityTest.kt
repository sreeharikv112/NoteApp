package com.noteapp.uicomponents.activities.makenote

import android.util.Log
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.android.material.textfield.TextInputLayout
import com.noteapp.R
import com.noteapp.uicomponents.activities.landing.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MakeNoteActivityTest {


    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MakeNoteActivity::class.java)


    /*<string name="note_title_error">Please enter Note Title</string>
    <string name="create_note_error">Please enter Note Details</string>*/

    @Test
    fun test_editfields_for_error_message_when_no_input() {

        val note_title_error = "Please enter Note Title"

        val note_desc_error = "Please enter Note Details"

        val titleEditText = onView(withId(R.id.addNoteTitle)).check(matches(isDisplayed()))

        val descriptionEditText = onView(withId(R.id.addNoteDescription)).check(matches(isDisplayed()))

        onView(withId(R.id.fab_make_note)).check(matches(isDisplayed())).perform(click())

        /*titleEditText.check(matches(hasTextInputLayoutHintText(mActivityTestRule.activity.
                getString(R.string.note_title_error))))*/

        titleEditText.check(matches(hasTextInputLayoutHintText("Note Title")))



    }

    //Helper methods
    fun hasTextInputLayoutHintText(expectedErrorText: String): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description?) { }

        override fun matchesSafely(item: View?): Boolean {
            if (item !is TextInputLayout) return false

            val errorText = item.editText!!.error as String
            Log.d("MTAG", "errorText - $errorText")

            val hintText = item.editText!!.hint as String
            Log.d("MTAG", "hintText - $hintText")
            /*val error = item.error ?: return false*/
            /*val hint = error.toString()*/
            return expectedErrorText == errorText
        }
    }
}