package com.freemusic.splashActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.freemusic.MainActivity
import com.freemusic.R
import com.freemusic.base.BaseActivity

class SplashScreen : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            this@SplashScreen.finish()
        }, 500)
    }
}