package com.yudas.pancasila_quiz_apk.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yudas.pancasila_quiz_apk.MainActivity
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.menu.QuizActivity
import com.yudas.pancasila_quiz_apk.menu.RankingActivity
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import com.yudas.pancasila_quiz_apk.retrofit.User
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ic_backlogin.setOnClickListener {
            finish()
        }

        btnLogin.setOnClickListener {
            DoLogin()
        }

        text_daftar.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed()
    {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        this@LoginActivity.finish()
    }


    private fun DoLogin() {
        val email = txt_email_login.text.toString()
        val pass = txt_password_login.text.toString()

        when{
            email.isEmpty() -> txt_email_login!!.error = "Email tidak boleh kosong !!"
            pass.isEmpty() -> txt_password_login!!.error = "Password tidak boleh kosong !!"

            else -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("Loading ..")
                dialog.show()
                val retrofit = Retrofit.Builder()
                    .baseUrl(URL.rest)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create<Functions>(Functions::class.java)
                val call = api.login(email, pass)
                call.enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        dialog.dismiss()
                        Toast.makeText(this@LoginActivity, "terjadi kesalahan , coba lagi !"+ t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {

                        val value = response.body() as User
                        if (value != null) {
                            if (!value.isError) {
                                Preferences(this@LoginActivity).setInt("STATUS_LOGIN", value.idUser)
                                Preferences(this@LoginActivity).setString("NAMA_USER", value.namaUser)

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            dialog.dismiss()
                            Toast.makeText(this@LoginActivity, value.message, Toast.LENGTH_SHORT).show()

                        }
                    }

                })
            }
        }
    }
}