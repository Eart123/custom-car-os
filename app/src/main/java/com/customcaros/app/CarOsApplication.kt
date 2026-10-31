package com.customcaros.app

import android.app.Application
import android.content.Context

/**
 * Uygulama sınıfı. Hilt kullanılmıyor (basit dependency injection ile yönetiliyor).
 * Tüm veriler yerel cihazda kalır.
 */
class CarOsApplication : Application() {

    companion object {
        @Volatile
        lateinit var instance: CarOsApplication
            private set

        fun getContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
