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

package com.openlewa.remotebar.service;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.annotation.VisibleForTesting;

import com.openlewa.remotebar.BuildConfig;
import com.openlewa.remotebar.R;
import com.openlewa.remotebar.util.U;

import static com.openlewa.remotebar.util.Constants.*;

@TargetApi(Build.VERSION_CODES.N)
public class QuickSettingsTileService extends TileService {
    @Override
    public void onStartListening() {
        super.onStartListening();
        updateState();
    }

    @Override
    public void onClick() {
        super.onClick();

        Intent intent = new Intent(U.isServiceRunning(this, NotificationService.class)
                ? ACTION_QUIT
                : ACTION_START);

        intent.setPackage(getPackageName());
        sendBroadcast(intent);

        U.newHandler().postDelayed(this::updateState, 100);
    }

    @VisibleForTesting
    void updateState() {
        Tile tile = getQsTile();
        if(tile != null) {
            tile.setIcon(Icon.createWithResource(this, R.drawable.tb_allapps));

            if(U.canDrawOverlays(this)) {
                tile.setState(
                        U.isServiceRunning(this, NotificationService.class)
                                ? Tile.STATE_ACTIVE
                                : Tile.STATE_INACTIVE
                );
            } else {
                tile.setState(Tile.STATE_UNAVAILABLE);
            }

            tile.updateTile();
        }
    }
}