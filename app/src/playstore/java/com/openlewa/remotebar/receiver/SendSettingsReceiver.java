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

package com.openlewa.remotebar.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;

import com.openlewa.remotebar.BuildConfig;
import com.openlewa.remotebar.backup.BackupUtils;
import com.openlewa.remotebar.backup.IntentBackupAgent;
import com.openlewa.remotebar.util.U;

import java.io.File;

import static com.openlewa.remotebar.util.Constants.*;

public class SendSettingsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Ignore this broadcast if this is the paid version
        if(context.getPackageName().equals(BuildConfig.BASE_APPLICATION_ID)) {
            Intent sendSettingsIntent = new Intent(ACTION_SEND_SETTINGS);
            sendSettingsIntent.setPackage(BuildConfig.PAID_APPLICATION_ID);

            BackupUtils.backup(context, new IntentBackupAgent(sendSettingsIntent));

            // Get images
            for(String filename : U.getImageFilenames()) {
                File file = new File(context.getFilesDir() + "/tb_images", filename);
                if(file.exists() && U.isPlayStoreRelease(context, BuildConfig.PAID_APPLICATION_ID)) {
                    Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                    context.grantUriPermission(BuildConfig.PAID_APPLICATION_ID, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    sendSettingsIntent.putExtra(filename, uri);
                }
            }

            // Finally, send the broadcast
            context.sendBroadcast(sendSettingsIntent);
        }
    }
}
