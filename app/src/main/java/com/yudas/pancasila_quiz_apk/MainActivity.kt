package com.yudas.pancasila_quiz_apk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yudas.pancasila_quiz_apk.auth.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quiz.setOnClickListener{
            val redirectlogin = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(redirectlogin)
        }
    }
}