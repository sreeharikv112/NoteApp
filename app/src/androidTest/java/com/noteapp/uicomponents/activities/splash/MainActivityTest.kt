package com.noteapp.uicomponents.activities.splash


import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.noteapp.R
import com.noteapp.uicomponents.activities.landing.MainActivity
import com.noteapp.uicomponents.activities.landing.NoteAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun splashActivityTest() {

        val title = "note title here abc"

        val description = "description here abc"

        onView(withId(R.id.fab)).check(matches(isDisplayed())).perform(click())

        val textInputEditText = onView(withId(R.id.addNoteTitle)).check(matches(isDisplayed()))

        textInputEditText.perform(replaceText(title), closeSoftKeyboard())

        val textInputEditText2 = onView(withId(R.id.addNoteDescription)).check(matches(isDisplayed()))

        textInputEditText2.perform(replaceText(description), closeSoftKeyboard())

        val materialButton = onView(withId(R.id.btnBlue)).check(matches(isDisplayed()))

        materialButton.perform(click())

        val floatingActionButton2 = onView(withId(R.id.fab_make_note)).check(matches(isDisplayed()))

        floatingActionButton2.perform(click())

        //Below is to scroll to last item
        /*onView(withId(R.id.listOfNoteRecyclerView)).perform(
        RecyclerViewActions.scrollToPosition<NoteAdapter.NoteViewHolder>(currentActivity.mRecyclerView.adapter!!.itemCount-1))*/

        onView(withId(R.id.listOfNoteRecyclerView)).perform(
                RecyclerViewActions.scrollToPosition<NoteAdapter.NoteViewHolder>(0))

        onView(withId(R.id.listOfNoteRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(title)))))

        onView(withId(R.id.listOfNoteRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(description)))))
    }

    fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View>): Matcher<View> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}
