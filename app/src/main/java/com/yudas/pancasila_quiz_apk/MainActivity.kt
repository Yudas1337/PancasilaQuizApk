package com.yudas.pancasila_quiz_apk

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yudas.pancasila_quiz_apk.auth.LoginActivity
import com.yudas.pancasila_quiz_apk.auth.Preferences
import com.yudas.pancasila_quiz_apk.menu.PengaturanActivity
import com.yudas.pancasila_quiz_apk.menu.QuizActivity
import com.yudas.pancasila_quiz_apk.menu.RankingActivity
import com.yudas.pancasila_quiz_apk.menu.TutorialActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_welcome.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        SharedPreferences
        val preference = Preferences(this)

        if (preference.getString("NAMA_USER", "")?.isEmpty()!!) {
            judulnya.setText("Pancasila Quiz")
        }else{
            ic_logout.visibility = View.VISIBLE
            judulnya.setText("Hai, "+preference.getString("NAMA_USER",""))
        }

        ic_logout.setOnClickListener{
            AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Apakah anda yakin akan logout?")
                    .setPositiveButton("Iya") { dialog, which ->
                        LogoutAct()
                        Toast.makeText(this, "Berhasil Logout!", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("Batal") { dialog, which ->
                        // do nothing
                    }
                    .show()

        }

        quiz.setOnClickListener{
            if (preference.getInt("STATUS_LOGIN", 0) == 0){
                val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Pemberitahuan")
                builder.setMessage("Anda harus Login untuk mengakses fitur ini !")


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
            if (preference.getInt("STATUS_LOGIN", 0) == 0){
                val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Pemberitahuan")
                builder.setMessage("Anda harus Login untuk mengakses fitur ini !")


                builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    val redirectlogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(redirectlogin)
                })
                builder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->

                })
                builder.show()
            }else{
                val intent = Intent(this@MainActivity, PengaturanActivity::class.java)
                startActivity(intent)
            }

        }

        tutorial.setOnClickListener{
            val intent = Intent(this@MainActivity, TutorialActivity::class.java)
            startActivity(intent)

        }
    }

    private fun LogoutAct()
    {
        val preference = Preferences(this)
        preference.clear()
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}