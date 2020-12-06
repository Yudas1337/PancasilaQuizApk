package com.yudas.pancasila_quiz_apk.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.yudas.pancasila_quiz_apk.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }, 3000L) //3000 L = 3 detik
    }
}