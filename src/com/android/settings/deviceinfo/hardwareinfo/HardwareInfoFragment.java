/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.hardwareinfo;

import static androidx.core.content.ContextCompat.getMainExecutor;

import android.app.settings.SettingsEnums;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.deviceinfo.imei.ImeiInfoPreferenceController;
import com.android.settings.deviceinfo.simstatus.SlotSimStatus;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// LINT.IfChange
@SearchIndexable
public class HardwareInfoFragment extends DashboardFragment {

    public static final String TAG = "HardwareInfo";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.DIALOG_SETTINGS_HARDWARE_INFO;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.hardware_info;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    public @Nullable String getPreferenceScreenBindingKey(@NonNull Context context) {
        return HardwareInfoScreen.KEY;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, this /* fragment */);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(
            Context context, HardwareInfoFragment fragment) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();

        final Executor executor = (fragment == null) ? getMainExecutor(context) :
                Executors.newSingleThreadExecutor();
        androidx.lifecycle.Lifecycle lifecycleObject = (fragment == null) ? null :
                fragment.getLifecycle();
        final SlotSimStatus slotSimStatus = new SlotSimStatus(context, executor, lifecycleObject);

        if (fragment != null) {
            addImeiController(controllers, context, fragment, slotSimStatus,
                    ImeiInfoPreferenceController.DEFAULT_KEY);
            for (int slotIndex = 0; slotIndex < slotSimStatus.size(); slotIndex++) {
                addImeiController(controllers, context, fragment, slotSimStatus,
                        ImeiInfoPreferenceController.DEFAULT_KEY + (1 + slotIndex));
            }
        }

        if (executor instanceof ExecutorService) {
            ((ExecutorService) executor).shutdown();
        }
        return controllers;
    }

    private static void addImeiController(List<AbstractPreferenceController> controllers,
            Context context, HardwareInfoFragment fragment, SlotSimStatus slotSimStatus,
            String imeiKey) {
        ImeiInfoPreferenceController imeiRecord =
                new ImeiInfoPreferenceController(context, imeiKey);
        imeiRecord.init(fragment, slotSimStatus);
        controllers.add(imeiRecord);
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.hardware_info) {

                @Override
                public List<AbstractPreferenceController> createPreferenceControllers(
                        Context context) {
                    return buildPreferenceControllers(context, null /* fragment */);
                }

                @Override
                protected boolean isPageSearchEnabled(Context context) {
                    return context.getResources().getBoolean(R.bool.config_show_device_model);
                }
            };
}
// LINT.ThenChange(HardwareInfoScreen.kt)
