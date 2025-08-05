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

package com.openlewa.remotebar.service;

import com.openlewa.remotebar.ui.UIHostService;
import com.openlewa.remotebar.ui.UIController;
import com.openlewa.remotebar.ui.RemotebarController;

public class RemotebarService extends UIHostService {
    @Override
    public UIController newController() {
        return new RemotebarController(this);
    }
}