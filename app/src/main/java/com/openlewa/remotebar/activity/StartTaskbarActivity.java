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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.openlewa.remotebar.service.NotificationService;
import com.openlewa.remotebar.util.ShortcutUtils;
import com.openlewa.remotebar.util.U;

import static com.openlewa.remotebar.util.Constants.*;

public class StartRemotebarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra(EXTRA_IS_LAUNCHING_SHORTCUT)) {
            Intent intent = new Intent(U.isServiceRunning(this, NotificationService.class)
                    ? ACTION_QUIT
                    : ACTION_START);

            intent.setPackage(getPackageName());
            sendBroadcast(intent);
        } else
            setResult(RESULT_OK, ShortcutUtils.getStartStopIntent(this));

        finish();
    }
}