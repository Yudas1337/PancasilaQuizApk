package com.yudas.pancasila_quiz_apk.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yudas.pancasila_quiz_apk.MainActivity
import com.yudas.pancasila_quiz_apk.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    internal lateinit var skor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = intent
        skor = intent.getStringExtra("skor")!!

        skorAkhir.setText(skor)

        button1.setOnClickListener {
            val intent = Intent(this@ResultActivity, QuizActivity::class.java)
            startActivity(intent)
            this@ResultActivity.finish()
        }

        button2.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            this@ResultActivity.finish()
        }

    }
}