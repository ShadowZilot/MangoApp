package com.example.mangoapp

import android.app.Application
import okhttp3.OkHttpClient

const val BASE_URL = "https://plannerok.ru/api/v1"
val GLOBAL_CLIENT = OkHttpClient()

class MangoApp : Application()