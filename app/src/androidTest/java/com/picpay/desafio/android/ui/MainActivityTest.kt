package com.picpay.desafio.android.ui

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import com.picpay.desafio.android.utils.RecyclerViewUtils.Companion.actionOnChild
import com.picpay.desafio.android.utils.RecyclerViewUtils.Companion.childOfViewAtPositionWithMatcher
import com.picpay.desafio.android.utils.RecyclerViewUtils.Companion.hasItemCount
import com.picpay.desafio.android.utils.RecyclerViewUtils.Companion.waitUntil
import com.picpay.desafio.android.ui.list.MainActivity
import org.hamcrest.number.OrderingComparison.greaterThan
import org.junit.Test


class MainActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testItemsOfRecyclerView() {

        val changeTxt = context.getString(R.string.change_text)

        launchActivity<MainActivity>().apply {
                onView(withId(R.id.recyclerView))
                    .perform(
                        waitUntil(hasItemCount(greaterThan(0))),
                        actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            5,
                            actionOnChild(
                                changeTxt,
                                R.id.name
                            )
                        )
                    )

                // Check if text has actually changed
                onView(withId(R.id.recyclerView))
                    .check(
                        matches(
                            childOfViewAtPositionWithMatcher(
                                R.id.name,
                                5,
                                withText(changeTxt)
                            )
                        )
                    )
            }

    }

}