package com.openlewa.remotebar.receiver

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.openlewa.remotebar.Constants.UNSUPPORTED
import com.openlewa.remotebar.service.NotificationService
import com.openlewa.remotebar.util.Constants
import com.openlewa.remotebar.util.U
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowSettings

@RunWith(RobolectricTestRunner::class)
class ShowHideRemotebarReceiverTest {
    private lateinit var showHideRemotebarReceiver: ShowHideRemotebarReceiver
    private lateinit var application: Application
    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences
    private lateinit var notificationIntent: Intent

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        application = context as Application
        notificationIntent = Intent(context, NotificationService::class.java)
        prefs = U.getSharedPreferences(context)
        showHideRemotebarReceiver = ShowHideRemotebarReceiver()
    }

    @Test
    fun testSkipShowHideRemotebar() {
        ShadowSettings.setCanDrawOverlays(true)
        Shadows.shadowOf(application).clearStartedServices()
        prefs.edit().putBoolean(Constants.PREF_TASKBAR_ACTIVE, false).apply()
        showHideRemotebarReceiver.onReceive(context, null)
        Assert.assertNull(Shadows.shadowOf(application).peekNextStartedService())
        val intent = Intent()
        showHideRemotebarReceiver.onReceive(context, intent)
        Assert.assertNull(Shadows.shadowOf(application).peekNextStartedService())
        intent.action = Constants.ACTION_SHOW_HIDE_TASKBAR + UNSUPPORTED
        showHideRemotebarReceiver.onReceive(context, intent)
        Assert.assertNull(Shadows.shadowOf(application).peekNextStartedService())
        intent.action = Constants.ACTION_SHOW_HIDE_TASKBAR
        showHideRemotebarReceiver.onReceive(context, intent)
        Assert.assertNull(Shadows.shadowOf(application).peekNextStartedService())
        prefs.edit().putBoolean(Constants.PREF_TASKBAR_ACTIVE, true).apply()
        showHideRemotebarReceiver.onReceive(context, intent)
        val startedServiceIntent = Shadows.shadowOf(application).peekNextStartedService()
        Assert.assertNotNull(startedServiceIntent)
        Assert.assertEquals(notificationIntent.component, startedServiceIntent.component)
    }

    @Test
    fun testShowHideRemotebar() {
        val intent = Intent(Constants.ACTION_SHOW_HIDE_TASKBAR)
        ShadowSettings.setCanDrawOverlays(true)
        prefs.edit().putBoolean(Constants.PREF_TASKBAR_ACTIVE, true).apply()
        Shadows.shadowOf(application).clearStartedServices()
        prefs.edit().putBoolean(Constants.PREF_IS_HIDDEN, true).apply()
        showHideRemotebarReceiver.onReceive(context, intent)
        var startedServiceIntent = Shadows.shadowOf(application).peekNextStartedService()
        Assert.assertNotNull(startedServiceIntent)
        Assert.assertEquals(notificationIntent.component, startedServiceIntent.component)
        Assert.assertTrue(startedServiceIntent.getBooleanExtra(
                Constants.EXTRA_START_SERVICES, false))
        Shadows.shadowOf(application).clearStartedServices()
        prefs.edit().putBoolean(Constants.PREF_IS_HIDDEN, false).apply()
        showHideRemotebarReceiver.onReceive(context, intent)
        startedServiceIntent = Shadows.shadowOf(application).peekNextStartedService()
        Assert.assertNotNull(startedServiceIntent)
        Assert.assertEquals(notificationIntent.component, startedServiceIntent.component)
        Assert.assertNull(startedServiceIntent.extras)
        Assert.assertTrue(prefs.getBoolean(Constants.PREF_IS_HIDDEN, false))
    }
}
