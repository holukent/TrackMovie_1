package com.chinlung.trackmovie

import android.app.Application

class MyApplication: Application() {
    val container = AppContainer(this)
}