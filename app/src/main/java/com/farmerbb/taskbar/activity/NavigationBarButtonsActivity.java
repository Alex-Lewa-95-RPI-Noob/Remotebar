/* Copyright 2017 Braden Farmer
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

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.openlewa.remotebar.R;
import com.openlewa.remotebar.service.TaskbarService;
import com.openlewa.remotebar.util.U;

import static com.openlewa.remotebar.util.Constants.*;

public class NavigationBarButtonsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    @SuppressWarnings("deprecation")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(R.string.tb_navigation_bar_buttons);

        addPreferencesFromResource(R.xml.tb_pref_navigation_bar_buttons);

        findPreference(PREF_BUTTON_BACK).setOnPreferenceClickListener(this);
        findPreference(PREF_BUTTON_HOME).setOnPreferenceClickListener(this);
        findPreference(PREF_BUTTON_RECENTS).setOnPreferenceClickListener(this);

        if(U.isShowHideNavbarSupported()
                && (U.isBlissOs(this) || U.isProjectSakura(this) || U.hasSupportLibrary(this, 7)))
            findPreference(PREF_AUTO_HIDE_NAVBAR).setOnPreferenceClickListener(this);
        else
            getPreferenceScreen().removePreference(findPreference(PREF_AUTO_HIDE_NAVBAR_CATEGORY));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch(preference.getKey()) {
            case PREF_AUTO_HIDE_NAVBAR:
                if(U.isServiceRunning(this, TaskbarService.class))
                    U.showHideNavigationBar(this, !((CheckBoxPreference) preference).isChecked());

                break;
            default:
                U.restartTaskbar(this);
                break;
        }
        return true;
    }
}