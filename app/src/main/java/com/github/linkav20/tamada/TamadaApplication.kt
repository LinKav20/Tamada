package com.github.linkav20.tamada

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TamadaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
