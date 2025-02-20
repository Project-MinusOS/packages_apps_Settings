package com.android.settings.deviceinfo.firmwareversion;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import androidx.preference.Preference;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.slices.Sliceable;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
public class MinusOSVersionCenter extends BasePreferenceController {
    private static final String TAG = "minusOSVersionDialogCtrl";
    private static final String KEY_MINUS_VERSION_PROP = "ro.minus.version";

    public MinusOSVersionCenter(Context context, String key) {
        super(context, key);
    }
    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
    
    @Override
    public CharSequence getSummary() {
        return SystemProperties.get(KEY_MINUS_VERSION_PROP,
                mContext.getString(R.string.unknown));
    }
}