package com.example.magicthegathering.ui.details.activity

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @get:Rule
    val activityTest = IntentsTestRule(DetailActivity::class.java)

    @Test
    fun givenInitialState_shouldHaveTheCardImage() {
        detailArrange {
            mockDetailActivityIntent()
        }

        detailAssert {
            checkCardImageInDetailActivity()
        }
    }

    @Test
    fun givenTheDetailsOfTheCard_whenClose_shouldGoToMainScreen() {
        detailArrange {
            mockMainActivityIntent()
            mockDetailActivityIntent()
        }

        closeAct {
            clickClose()
        }

        detailAssert {
            checkMainActivityWasStarted()
        }
    }
}