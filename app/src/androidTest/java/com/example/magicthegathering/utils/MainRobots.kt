@file:Suppress("ClassName")

package com.example.magicthegathering.utils

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.example.magicthegathering.R

class mainArrange(action: mainArrange.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun mockMainActivityIntent() {
        intending(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }
}


class mainAssert(action: mainAssert.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun checkHomeFragmentInMainActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.home_bottom))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkBottomNavigationViewInMainActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.bottomNav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}