package com.example.magicthegathering.utils

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.magicthegathering.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityTest = IntentsTestRule(MainActivity::class.java)

    @Test
    fun givenInitialState_shouldHaveBottomNavigationView() {
        mainArrange() {
            mockMainActivityIntent()
        }

        mainAssert() {
            checkBottomNavigationViewInMainActivity()
        }
    }

    @Test
    fun givenInitialState_shouldHaveHomeFragment() {
        mainArrange() {
            mockMainActivityIntent()
        }

        mainAssert() {
            checkHomeFragmentInMainActivity()
        }
    }
}
