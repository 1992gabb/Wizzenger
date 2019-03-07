package com.bombardier_gabriel.wizzenger

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.bombardier_gabriel.wizzenger.activities.HomeActivity
import com.bombardier_gabriel.wizzenger.activities.LoginActivity
import junit.framework.AssertionFailedError
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import android.app.Activity
import com.bombardier_gabriel.wizzenger.activities.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import junit.framework.Assert.assertTrue
import java.security.AccessController.getContext


@RunWith(AndroidJUnit4::class)
@LargeTest
class UiTests {

    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun connectionWorks() {
        try {
            onView(withId(R.id.edit_text_password)).perform(typeText(""), closeSoftKeyboard())

        } catch (e: Exception) {
            onView(withId(R.id.settings_btn)).perform(click())
            onView(withId(R.id.settings_button_logout)).perform(click())
        }

        onView(withId(R.id.edit_text_password)).perform(typeText("AAAaaa111"), closeSoftKeyboard())
        onView(withId(R.id.btnConnect)).perform(click())

        Thread.sleep(2000)

        //Voir si on est connecté
        onView(withId(R.id.main_title)).perform(click())
    }

    @Test
    fun logout() {
        //Tester en regardant l'activité, ne fonctionne pas
        //Intents.init()
        //intended(hasComponent(ComponentName(InstrumentationRegistry.getInstrumentation().getTargetContext(), HomeActivity::class.java)))
        //intended(hasComponent(HomeActivity::class.java!!.getName()))


        //Si on est offline, se connecter

        //Si on est deja dans settings, on se logout

        //Sinon, on doit y aller en premier


        Thread.sleep(1000)
        onView(withId(R.id.settings_btn)).perform(click())
        onView(withId(R.id.settings_button_logout)).perform(click())

        //Voir si on est déconnecté
        onView(withId(R.id.edit_text_password)).perform(click())
    }

    @Test
    fun sendMessage() {
        //On vérifie si on est connecté
        try {
            onView(withId(R.id.settings_btn)).perform(click())

        } catch (e: Exception) {
            onView(withId(R.id.edit_text_password)).perform(typeText(""), closeSoftKeyboard())
            onView(withId(R.id.edit_text_password)).perform(typeText("AAAaaa111"), closeSoftKeyboard())
            onView(withId(R.id.btnConnect)).perform(click())
        }

        //On entre dans une convo
        Thread.sleep(3000)
        onView(withId(R.id.recycler_conversations))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.send_button)).perform(click())

        //On envoie le message
        val msg = "Bonjour mon cher monsieur"
        onView(withId(R.id.message_entry_zone)).perform(typeText(msg))
        onView(withId(R.id.send_button)).perform(click())
        Thread.sleep(1000)
    }

//    fun assertCurrentActivityIsInstanceOf(activityClass: Class<out Activity>) {
//        val currentActivity = getActivityInstance()
//        checkNotNull(currentActivity)
//        checkNotNull(activityClass)
//        assertTrue(currentActivity.javaClass.isAssignableFrom(activityClass))
//    }


}
