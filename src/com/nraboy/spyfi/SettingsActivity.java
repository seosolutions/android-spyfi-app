package com.nraboy.spyfi;

import android.os.*;
import android.app.*;
import android.view.*;
import android.content.*;
import android.preference.*;
import android.preference.Preference.*;
import android.net.*;
import android.preference.*;
import com.google.analytics.tracking.android.EasyTracker;

public class SettingsActivity extends PreferenceActivity {

    private Preference appVersion;
    private Preference appRate;
    private Preference appSupport;
    private Preference appTwitter;
    private String appVersionStr;
    private SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        appVersionStr = getResources().getString(R.string.dialog_message_version) + " " + getResources().getString(R.string.app_version);
        appVersion = (Preference) findPreference("pref_key_app_version");
        appRate = (Preference) findPreference("pref_key_app_rate");
        appSupport = (Preference) findPreference("pref_key_support_contact");
        appTwitter = (Preference) findPreference("pref_key_app_twitter");
        appRate.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                String app_store = getResources().getString(R.string.app_market).equals("Google Play Edition") ? getResources().getString(R.string.app_store_google) : getResources().getString(R.string.app_store_amazon);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(app_store));
                startActivity(browserIntent);
                return true;
            }
        });
        appSupport.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_support)));
                startActivity(browserIntent);
                return true;
            }
        });
        appTwitter.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.app_twitter)));
                startActivity(browserIntent);
                return true;
            }
        });
        appVersion.setTitle(appVersionStr);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:             
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(this.settings.getBoolean("pref_key_analytics", true)) {
            EasyTracker.getInstance(this).activityStart(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(this.settings.getBoolean("pref_key_analytics", true)) {
            EasyTracker.getInstance(this).activityStop(this);
        }
    }
    
}
