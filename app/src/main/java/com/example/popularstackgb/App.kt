package com.example.popularstackgb

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import com.example.popularstackgb.data.loginusecase.LoginUsecaseImpl
import com.example.popularstackgb.data.loginapi.MockLoginApiImpl
import com.example.popularstackgb.domain.LoginApi
import com.example.popularstackgb.domain.LoginUsecase

class App : Application() {
    private val loginApi: LoginApi by lazy { MockLoginApiImpl() }
    val loginUsecase: LoginUsecase by lazy {
        LoginUsecaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }

val Context.res: Resources
    get() {
        return resources
    }