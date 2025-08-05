/* Copyright 2019 Braden Farmer
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

package com.openlewa.remotebar.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.TileService;

import com.openlewa.remotebar.R;
import com.openlewa.remotebar.activity.ShortcutActivity;
import com.openlewa.remotebar.activity.StartRemotebarActivity;
import com.openlewa.remotebar.service.FavoriteApp1;
import com.openlewa.remotebar.service.FavoriteApp2;
import com.openlewa.remotebar.service.FavoriteApp3;
import com.openlewa.remotebar.service.FavoriteApp4;
import com.openlewa.remotebar.service.FavoriteApp5;

import static com.openlewa.remotebar.util.Constants.*;

public class ShortcutUtils {

    private ShortcutUtils() {}

    public static Intent getShortcutIntent(Context context) {
        Intent shortcutIntent = new Intent(context, ShortcutActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.putExtra(EXTRA_IS_LAUNCHING_SHORTCUT, true);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.tb_freeform_mode));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.tb_pref_header_freeform));

        return intent;
    }

    public static Intent getStartStopIntent(Context context) {
        Intent shortcutIntent = new Intent(context, StartRemotebarActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.putExtra(EXTRA_IS_LAUNCHING_SHORTCUT, true);

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.tb_launcher));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.tb_start_remotebar));

        return intent;
    }

    public static void initFavoriteAppTiles(Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return;

        Class<?>[] tiles = new Class<?>[] {
                FavoriteApp1.class,
                FavoriteApp2.class,
                FavoriteApp3.class,
                FavoriteApp4.class,
                FavoriteApp5.class
        };

        for(Class<?> tile : tiles) {
            TileService.requestListeningState(context, new ComponentName(context, tile));
        }
    }
}
