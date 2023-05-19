package com.example.mangoapp

import android.app.Application
import com.example.mangoapp.registration.data.UserAuthData
import okhttp3.OkHttpClient

const val BASE_URL = "https://plannerok.ru/api/v1"
val GLOBAL_CLIENT = OkHttpClient()

class MangoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        UserAuthData.Base.Instance.create(this)
    }
}