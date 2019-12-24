package com.example.android.contactsapp

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.contactsapp.CustomAssertions.Companion.hasItemCount
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.not
import org.junit.runner.Description
import java.util.regex.Matcher

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class CustomAssertions {
    companion object {
        fun hasItemCount(count: Int): ViewAssertion {
            return RecyclerViewItemCountAssertion(count)
        }
    }

    private class RecyclerViewItemCountAssertion(private val count: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            if (view !is RecyclerView) {
                throw IllegalStateException("The asserted view is not RecyclerView")
            }

            if (view.adapter == null) {
                throw IllegalStateException("No adapter is assigned to RecyclerView")
            }

            ViewMatchers.assertThat("RecyclerView item count ", view.adapter!!.itemCount, CoreMatchers.equalTo(count))
        }
    }
}

@RunWith(AndroidJUnit4::class)
class LocalContactsTest {

    lateinit var scenario: ActivityScenario<MainActivity>
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun useAppContext() {
        // Context of the app under test.
        assertEquals("com.example.android.contactsapp", appContext.packageName)
    }

    @Test
    fun menuIsWorking() {
        val intent = Intent(appContext.applicationContext, MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)

        openActionBarOverflowOrOptionsMenu(appContext)
        onView(withText(appContext.getString(R.string.contact_menu_display_both))).check(matches(isDisplayed()))
        onView(withText(appContext.getString(R.string.contact_menu_display_cloud))).check(matches(isDisplayed()))
        onView(withText(appContext.getString(R.string.contact_menu_display_local))).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun menuLocalOptionsClickIsWorking() {
        val intent = Intent(appContext.applicationContext, MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)

        openActionBarOverflowOrOptionsMenu(appContext)
        onView(withText(appContext.getString(R.string.contact_menu_display_local))).perform(click())
        onView(withText("Shivam Gupta")).check(matches(isDisplayed()))
        onView(withText("John Smith")).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).check(hasItemCount(2))
    }

    @Test
    fun menuAPIOptionsClickIsWorking() {
        val intent = Intent(appContext.applicationContext, MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)

        openActionBarOverflowOrOptionsMenu(appContext)
        onView(withText(appContext.getString(R.string.contact_menu_display_cloud))).perform(click())
        onView(withId(R.id.recycler_view)).check(hasItemCount(10))
    }

    @Test
    fun menuBothOptionsClickIsWorking() {
        val intent = Intent(appContext.applicationContext, MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)

        openActionBarOverflowOrOptionsMenu(appContext)
        onView(withText(appContext.getString(R.string.contact_menu_display_both))).perform(click())
        onView(withId(R.id.recycler_view)).check(hasItemCount(12))
    }
}
