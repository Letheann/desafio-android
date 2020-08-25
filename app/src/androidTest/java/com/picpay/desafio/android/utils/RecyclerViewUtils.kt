package com.picpay.desafio.android.utils

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.StringDescription
import org.hamcrest.core.AllOf.allOf


class RecyclerViewUtils {
    companion object {

        /**
         * Performs an action on a view with a given id inside a RecyclerView's item
         */
        fun actionOnChild(text: String, childId: Int): ViewAction {
            return object : ViewAction {
                override fun getDescription(): String {
                    return "Perform action on the view whose id is passed in"
                }

                override fun getConstraints(): Matcher<View> {
                    return allOf(isDisplayed(), isAssignableFrom(View::class.java))
                }

                override fun perform(uiController: UiController?, view: View?) {
                    view?.let {
                        val child: AppCompatTextView = it.findViewById(childId)
                        child.text = text
                    }
                }

            }
        }

        /**
         * checks that the matcher childMatcher matches a view having a given id
         * inside a RecyclerView's item (given its position)
         */
        fun childOfViewAtPositionWithMatcher(
            childId: Int,
            position: Int,
            childMatcher: Matcher<View>
        ): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText(
                        "Checks that the matcher childMatcher matches" +
                                " with a view having a given id inside a RecyclerView's item (given its position)"
                    )
                }

                override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
                    val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
                    val matcher = hasDescendant(allOf(withId(childId), childMatcher))
                    return viewHolder != null && matcher.matches(viewHolder.itemView)
                }

            }
        }

        fun waitUntil(matcher: Matcher<View?>): ViewAction? {
            return actionWithAssertions(object :
                ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(View::class.java)
                }

                override fun getDescription(): String {
                    val description = StringDescription()
                    matcher.describeTo(description)
                    return String.format("wait until: %s", description)
                }

                override fun perform(
                    uiController: UiController,
                    view: View
                ) {
                    if (!matcher.matches(view)) {
                        val callback =
                            LayoutChangeCallback(
                                matcher
                            )
                        try {
                            IdlingRegistry.getInstance().register(callback)
                            view.addOnLayoutChangeListener(callback)
                            uiController.loopMainThreadUntilIdle()
                        } finally {
                            view.removeOnLayoutChangeListener(callback)
                            IdlingRegistry.getInstance().unregister(callback)
                        }
                    }
                }
            })
        }

        fun hasItemCount(matcher: Matcher<Int>): Matcher<View?> {
            return object :
                BoundedMatcher<View?, RecyclerView>(
                    RecyclerView::class.java
                ) {
                override fun describeTo(description: Description) {
                    description.appendText("has item count: ")
                    matcher.describeTo(description)
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    return matcher.matches(view.adapter!!.itemCount)
                }
            }
        }


        private class LayoutChangeCallback internal constructor(private val matcher: Matcher<View?>) :
            IdlingResource, View.OnLayoutChangeListener {
            private var callback: IdlingResource.ResourceCallback? = null

            var idle = false

            override fun getName(): String {
                return "Layout change callback"
            }

            override fun isIdleNow(): Boolean {
                return idle
            }

            override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
                this.callback = callback
            }

            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                idle = matcher.matches(v)
                callback?.onTransitionToIdle()
            }

        }

    }
}