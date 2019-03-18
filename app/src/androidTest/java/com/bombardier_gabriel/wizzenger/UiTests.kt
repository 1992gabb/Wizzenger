package com.bombardier_gabriel.wizzenger

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.action.ViewActions.swipeRight
import android.support.v7.widget.RecyclerView
import com.bombardier_gabriel.wizzenger.activities.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest

//Si on voulait mettre les tests en ordre croissant
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class UiTests {

    //Pour déterminer le point de départ du test
    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun connectionWorks() {
        try {
            //Vérifier qu'on est dans la page login, sinon on se logout
            onView(withId(R.id.login_title)).check(matches(withText("Wizzenger")))

        } catch (e: Exception) {
            //logout avant de se connecter
            Thread.sleep(1000)
            logoutAction()
        }

        loginAction()

        Thread.sleep(1000)

        //Voir si on est connecté
        onView(withId(R.id.main_title)).check(matches(withText("Wizzenger")))
    }

    @Test
    fun logout() {
        checkConnection()

        Thread.sleep(1000)

        logoutAction()

        //Voir si on est déconnecté
        onView(withId(R.id.login_title)).check(matches(withText("Wizzenger")))
    }

    //@Test
    fun sendMessage() {
        checkConnection()

        //On entre dans une convo
        Thread.sleep(2000)
        onView(withId(R.id.recycler_conversations))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //On envoie le message
        onView(withId(R.id.message_entry_zone)).perform(typeText("Bonjour mon cher monsieur"), closeSoftKeyboard())
        onView(withId(R.id.send_button)).perform(click())
        Thread.sleep(1000)
    }

    //@Test
    fun removeContact(){
        checkConnection()

        onView(withId(R.id.view_pager)).perform(swipeLeft())
        Thread.sleep(1000)

        onView(withId(R.id.recycler_contacts))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)

        onView(withId(R.id.contact_remove_button)).perform(click())
        onView(withId(R.id.alert_button_no)).perform(click())

        //Normalement, regarder ici si le contact est encore dans la liste
    }

    fun checkConnection(){
        try {
            //On vérifie si on est connecté, sinon on se connecte
            onView(withId(R.id.main_title)).check(matches(withText("Wizzenger")))
        } catch (e: Exception) {
            //Pour se connecter avant de faire une action
            loginAction()
        }
    }

    fun logoutAction(){
        onView(withId(R.id.settings_btn)).perform(click())
        onView(withId(R.id.settings_button_logout)).perform(click())
    }

    fun loginAction(){
        onView(withId(R.id.edit_text_password)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password)).perform(typeText("AAAaaa111"), closeSoftKeyboard())
        onView(withId(R.id.btnConnect)).perform(click())
        Thread.sleep(1000)
    }
}
