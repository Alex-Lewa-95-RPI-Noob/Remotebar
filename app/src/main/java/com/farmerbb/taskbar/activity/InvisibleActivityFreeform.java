/* Copyright 2016 Braden Farmer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openlewa.remotebar.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import com.openlewa.remotebar.service.DashboardService;
import com.openlewa.remotebar.service.NotificationService;
import com.openlewa.remotebar.service.StartMenuService;
import com.openlewa.remotebar.service.TaskbarService;
import com.openlewa.remotebar.helper.FreeformHackHelper;
import com.openlewa.remotebar.helper.LauncherHelper;
import com.openlewa.remotebar.util.U;

import static com.openlewa.remotebar.util.Constants.*;

public class InvisibleActivityFreeform extends Activity {

    boolean showTaskbar = false;
    boolean doNotHide = false;
    boolean proceedWithOnCreate = true;
    boolean finish = false;
    boolean bootToFreeform = false;
    boolean initialLaunch = true;

    private final BroadcastReceiver appearingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doNotHide = true;
        }
    };

    private final BroadcastReceiver disappearingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doNotHide = false;
        }
    };

    private final BroadcastReceiver finishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            reallyFinish();
        }
    };

    @SuppressLint("HardwareIds")
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FreeformHackHelper helper = FreeformHackHelper.getInstance();
        if(helper.isFreeformHackActive()) {
            proceedWithOnCreate = false;
            super.finish();
        }

        if(getIntent().hasExtra("check_multiwindow")) {
            showTaskbar = false;

            if(!isInMultiWindowMode()) {
                proceedWithOnCreate = false;
                super.finish();
            }
        }

        if(U.isOverridingFreeformHack(this)) {
            helper.setFreeformHackActive(true);
            helper.setInFreeformWorkspace(true);

            proceedWithOnCreate = false;
            super.finish();
            overridePendingTransition(0, 0);
        }

        if(!proceedWithOnCreate)
            return;

        // Detect outside touches, and pass them through to the underlying activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        U.registerReceiver(this, appearingReceiver,
                ACTION_START_MENU_APPEARING,
                ACTION_CONTEXT_MENU_APPEARING,
                ACTION_DASHBOARD_APPEARING);

        U.registerReceiver(this, disappearingReceiver,
                ACTION_START_MENU_DISAPPEARING,
                ACTION_CONTEXT_MENU_DISAPPEARING,
                ACTION_DASHBOARD_DISAPPEARING);

        U.registerReceiver(this, finishReceiver, ACTION_FINISH_FREEFORM_ACTIVITY);

        helper.setFreeformHackActive(true);

        // Show power button warning on CyanogenMod / LineageOS devices
        if(getPackageManager().hasSystemFeature("com.cyanogenmod.android")) {
            SharedPreferences pref = U.getSharedPreferences(this);
            if(!pref.getString(PREF_POWER_BUTTON_WARNING, "null").equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))) {
                U.newHandler().postDelayed(() -> {
                    if(helper.isInFreeformWorkspace()) {
                        Intent intent = U.getThemedIntent(this, InvisibleActivityAlt.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("power_button_warning", true);

                        U.startActivityMaximized(U.getDisplayContext(this), intent);
                    }
                }, 100);
            }
        }

        showTaskbar = true;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();

        // Show the taskbar when activity is resumed (no other freeform windows are active)
        if(showTaskbar) {
            U.sendBroadcast(this, ACTION_SHOW_TASKBAR);
        }

        if(!isInMultiWindowMode() && !initialLaunch) {
            reallyFinish();
        }

        initialLaunch = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(!proceedWithOnCreate)
            return;

        U.unregisterReceiver(this, appearingReceiver);
        U.unregisterReceiver(this, disappearingReceiver);
        U.unregisterReceiver(this, finishReceiver);

        cleanup();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FreeformHackHelper.getInstance().setInFreeformWorkspace(true);

        if(U.launcherIsDefault(this) && !U.isChromeOs(this)) {
            LauncherHelper.getInstance().setOnPrimaryHomeScreen(true);
            bootToFreeform = true;

            SharedPreferences pref = U.getSharedPreferences(this);
            if(pref.getBoolean(PREF_FIRST_RUN, true)) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(PREF_FIRST_RUN, false);
                editor.putBoolean(PREF_COLLAPSED, true);
                editor.apply();

                U.newHandler().postDelayed(() -> {
                    Intent intent = new Intent(this, DummyActivity.class);
                    intent.putExtra(EXTRA_SHOW_RECENT_APPS_DIALOG, true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                }, 250);
            }

            // We always start the Taskbar and Start Menu services, even if the app isn't normally running
            startService(new Intent(this, TaskbarService.class));
            startService(new Intent(this, StartMenuService.class));
            startService(new Intent(this, DashboardService.class));

            if(pref.getBoolean(PREF_TASKBAR_ACTIVE, false) && !U.isServiceRunning(this, NotificationService.class))
                pref.edit().putBoolean(PREF_TASKBAR_ACTIVE, false).apply();

            // Show the taskbar when activity is started
            if(showTaskbar) {
                U.newHandler().postDelayed(() ->
                        U.sendBroadcast(this, ACTION_SHOW_TASKBAR), 100);
            }
        }

        // Show the taskbar when activity is started
        if(showTaskbar) {
            U.sendBroadcast(this, ACTION_SHOW_TASKBAR);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        possiblyHideTaskbar();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(!finish)
            FreeformHackHelper.getInstance().setInFreeformWorkspace(false);

        possiblyHideTaskbar();

        if(bootToFreeform && !finish) {
            LauncherHelper.getInstance().setOnPrimaryHomeScreen(false);
            bootToFreeform = false;

            // Stop the Taskbar and Start Menu services if they should normally not be active
            SharedPreferences pref = U.getSharedPreferences(this);
            if(!pref.getBoolean(PREF_TASKBAR_ACTIVE, false) || pref.getBoolean(PREF_IS_HIDDEN, false)) {
                stopService(new Intent(this, TaskbarService.class));
                stopService(new Intent(this, StartMenuService.class));
                stopService(new Intent(this, DashboardService.class));

                U.clearCaches(this);
            }
        }
    }

    // We don't want this activity to finish under normal circumstances
    @Override
    public void finish() {}

    private void possiblyHideTaskbar() {
        if(!doNotHide) {
            U.newHandler().postDelayed(() -> {
                if(U.shouldCollapse(this, false)
                        && !LauncherHelper.getInstance().isOnHomeScreen(this)) {
                    U.sendBroadcast(this, ACTION_HIDE_TASKBAR);
                } else {
                    U.sendBroadcast(this, ACTION_HIDE_START_MENU);
                }
            }, 100);
        }
    }

    private void reallyFinish() {
        super.finish();
        overridePendingTransition(0, 0);

        cleanup();
    }

    private void cleanup() {
        if(!finish) {
            FreeformHackHelper helper = FreeformHackHelper.getInstance();
            helper.setFreeformHackActive(false);
            helper.setInFreeformWorkspace(false);

            finish = true;
        }
    }
}