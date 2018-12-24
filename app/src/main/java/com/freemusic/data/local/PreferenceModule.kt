package com.freemusic.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
class PreferenceModule {
    internal fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.applicationContext.getSharedPreferences(
            application.applicationContext.packageName,
            Context.MODE_PRIVATE
        )
    }
}
