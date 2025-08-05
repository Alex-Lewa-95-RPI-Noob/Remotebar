package com.openlewa.remotebar.service

import com.openlewa.remotebar.ui.DashboardController
import com.openlewa.remotebar.ui.StartMenuController
import com.openlewa.remotebar.ui.RemotebarController
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UIHostServiceNewControllerTest {
    @Test
    fun testDashboardService() {
        val dashboardService = Robolectric.setupService(DashboardService::class.java)
        Assert.assertTrue(dashboardService.newController() is DashboardController)
    }

    @Test
    fun testStartMenuService() {
        val startMenuService = Robolectric.setupService(StartMenuService::class.java)
        Assert.assertTrue(startMenuService.newController() is StartMenuController)
    }

    @Test
    fun testRemotebarService() {
        val taskbarService = Robolectric.setupService(RemotebarService::class.java)
        Assert.assertTrue(taskbarService.newController() is RemotebarController)
    }
}
