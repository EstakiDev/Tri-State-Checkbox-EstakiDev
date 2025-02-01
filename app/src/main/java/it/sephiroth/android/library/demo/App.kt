package it.sephiroth.android.library.demo

import android.app.Application
import it.sephiroth.android.library.checkbox3state.isDebugMode

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        isDebugMode = BuildConfig.DEBUG
    }
}