package com.izzaweb.dicodingfootbalapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.text.TextUtils.replace
import com.izzaweb.dicodingfootbalapps.R.id.*
import org.junit.Rule
import org.junit.Test

class NextMatchFragmentTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerView(){
            onView(withId(listNextMatch))
                    .check(matches(isDisplayed()))
            onView(withId(listNextMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
            onView(withId(listNextMatch)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10,click())
            )
        }
    @Test
    fun testAppBehaviourNextMatch(){
        //test nextmatch
        Thread.sleep(7000)
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        onView(withId(listNextMatch))
                .check(matches(isDisplayed()))
      //  onView(withId(listNextMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(listNextMatch)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click())
        )

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorit)).perform(click())


        //testprevmatch
        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(prev)).perform(click())

        Thread.sleep(7000)
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        onView(withId(listPrevMatch))
                .check(matches(isDisplayed()))
        //  onView(withId(listNextMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(listPrevMatch)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click())
        )

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        pressBack()

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorit)).perform(click())

    }
}