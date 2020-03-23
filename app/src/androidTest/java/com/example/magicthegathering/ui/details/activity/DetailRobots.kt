@file:Suppress("ClassName")

package com.example.magicthegathering.ui.details.activity

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.example.magicthegathering.R
import com.example.magicthegathering.utils.MainActivity

class detailArrange(action: detailArrange.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun mockMainActivityIntent() {
        intending(IntentMatchers.hasComponent(MainActivity::class.java.name))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null))
    }

    fun mockDetailActivityIntent() {
        Intents.intending(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }
}

class closeAct(action: closeAct.() -> Unit){
    init {
        action.invoke(this)
    }

    fun clickClose() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_image))
            .perform(ViewActions.click())
    }
}

class detailAssert(action: detailAssert.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun checkCardImageInDetailActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.img_card_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkMainActivityWasStarted(){
        Intents.intending(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }
}