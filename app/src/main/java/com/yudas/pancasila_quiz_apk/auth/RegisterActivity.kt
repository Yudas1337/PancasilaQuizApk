package com.yudas.pancasila_quiz_apk.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.ProgressDialog
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginButton.setOnClickListener {
            val redirectlogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(redirectlogin)
        }

        daftarButton.setOnClickListener{
            DoRegister()
        }

        ic_back.setOnClickListener{
            finish()
        }

    }

    private fun DoRegister()
    {
        val namaInput     = namaUser.text.toString()
        val emailInput    = emailUser.text.toString()
        val telpInput     = hpUser.text.toString()
        val passwordInput = passUser.text.toString()

        when{
            namaInput.isEmpty() -> namaUser!!.error = "Nama tidak boleh kosong"
            emailInput.isEmpty()-> emailUser!!.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> hpUser!!.error = "Telepon tidak boleh kosong"
            passwordInput.isEmpty() -> passUser!!.error = "Password tidak boleh kosong"
            else -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("Loading ..")
                dialog.show()
                val retrofit = Retrofit.Builder()
                    .baseUrl(URL.rest)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create<Functions>(Functions::class.java)
                val call = api.register(namaInput, emailInput, telpInput , passwordInput)

                call.enqueue(object : Callback<Value> {
                    override fun onResponse(call: Call<Value>, response: Response<Value>) {
                        val value = response.body() as Value
                        if (value != null) {
                            if (!value.isError) {
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                this@RegisterActivity.finish()
                            }
                            dialog.dismiss()
                            Toast.makeText(this@RegisterActivity, value.message, Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<Value>, t: Throwable) {
                        dialog.dismiss()
                        Toast.makeText(this@RegisterActivity, "terjadi kesalahan , coba lagi !", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}