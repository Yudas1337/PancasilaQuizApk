package com.yudas.pancasila_quiz_apk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yudas.pancasila_quiz_apk.auth.RegisterActivity
import com.yudas.pancasila_quiz_apk.menu.PengaturanActivity
import com.yudas.pancasila_quiz_apk.menu.QuizActivity
import com.yudas.pancasila_quiz_apk.menu.RankingActivity
import com.yudas.pancasila_quiz_apk.menu.TutorialActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quiz.setOnClickListener{
            val intent = Intent(this@MainActivity, QuizActivity::class.java)
            startActivity(intent)
        }

        ranking.setOnClickListener{
            val intent = Intent(this@MainActivity, RankingActivity::class.java)
            startActivity(intent)
        }

        pengaturan.setOnClickListener {
            val intent = Intent(this@MainActivity, PengaturanActivity::class.java)
            startActivity(intent)
        }

        tutorial.setOnClickListener{
            val intent = Intent(this@MainActivity, TutorialActivity::class.java)
            startActivity(intent)
        }
    }
}