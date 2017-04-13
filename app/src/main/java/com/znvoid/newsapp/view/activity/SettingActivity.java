package com.znvoid.newsapp.view.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.znvoid.newsapp.R;

/**
 * Created by zn on 2017/4/12.
 */

public class SettingActivity extends BaseSlidingPaneActivity {

    private SettingsFragment mSettingsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            getFragmentManager().beginTransaction().replace(R.id.setting_container, mSettingsFragment).commit();
        }

    }


    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

            if ("yesno_save__info".equals(preference.getKey())) {
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("yesno_save__info");
                EditTextPreference editTextPreference1 = (EditTextPreference) findPreference("user_info_name");
                EditTextPreference editTextPreference2 = (EditTextPreference) findPreference("user_info_city");
                editTextPreference1.setEnabled(checkBoxPreference.isChecked());
                editTextPreference2.setEnabled(checkBoxPreference.isChecked());
            }
            if ("关于".equals(preference.getTitle())){
                Toast.makeText(getActivity(),"这是个Android应用-_-",Toast.LENGTH_SHORT).show();
            }

            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }
}
