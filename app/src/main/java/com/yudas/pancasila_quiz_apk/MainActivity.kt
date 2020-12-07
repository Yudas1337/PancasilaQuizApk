package com.yudas.pancasila_quiz_apk

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.internal.ContextUtils.getActivity
import com.yudas.pancasila_quiz_apk.auth.LoginActivity
import com.yudas.pancasila_quiz_apk.auth.Preferences
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

        //        SharedPreferences
        val preference = Preferences(this)

        quiz.setOnClickListener{
            if (preference.getInt("STATUS_LOGIN", 0) == 0){
                val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Pemberitahuan")
                builder.setMessage("Apakah anda ingin login ?")


                builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    val redirectlogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(redirectlogin)
                })
                builder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->

                })
                builder.show()
            }else{
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                startActivity(intent)
            }
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