/*
 * Copyright (C) 2026 The MinusOS Project
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

package com.android.settings.deviceinfo.aboutphone

import android.content.Context
import androidx.preference.Preference
import com.android.settings.R
import com.android.settingslib.metadata.PreferenceMetadata
import com.android.settingslib.metadata.UI_ONLY_PREFERENCE
import com.android.settingslib.preference.PreferenceBinding
import com.android.settingslib.widget.LayoutPreference

class MinusOsLogoPreference : PreferenceMetadata, PreferenceBinding {

    override val key: String
        get() = "minus_os_logo_card"

    override val purpose: Int
        get() = R.string.minus_os_logo_purpose

    override val title: Int
        get() = R.string.minus_os_logo

    override val indexable
        get() = false

    override fun tags(context: Context) = arrayOf(UI_ONLY_PREFERENCE)

    override fun createWidget(context: Context): Preference =
        LayoutPreference(context, R.layout.minus_os_logo_card)

    override fun bind(preference: Preference, metadata: PreferenceMetadata) {
        super.bind(preference, metadata)
        preference.isSelectable = false
    }
}
