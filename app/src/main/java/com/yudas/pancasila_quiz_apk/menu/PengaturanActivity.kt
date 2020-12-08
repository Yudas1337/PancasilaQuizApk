package com.yudas.pancasila_quiz_apk.menu

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import com.yudas.pancasila_quiz_apk.MainActivity
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.auth.Preferences
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import com.yudas.pancasila_quiz_apk.retrofit.User
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_pengaturan.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.custom_dialog_password.view.*
import kotlinx.android.synthetic.main.custom_dialog_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PengaturanActivity : AppCompatActivity() {

    private var idUser: Int = 0
    private lateinit var mNama: String
    private lateinit var mNoHp: String
    private lateinit var mEmail: String
    private lateinit var mPassword: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        //        SharedPreferences
        val preference = Preferences(this)
        idUser = preference.getInt("STATUS_LOGIN", 0)

        loadUser()

        pengaturan_logout.setOnClickListener {
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

        edit_profil.setOnClickListener {
            val customView: View = layoutInflater.inflate(R.layout.custom_dialog_profile, null)
            customView.edit_nama.setText(mNama)
            customView.edit_noHp.setText(mNoHp)
            customView.edit_email.setText(mEmail)

            val diaog : AlertDialog? = AlertDialog.Builder(this)
                .setTitle("Edit Profil")
                .setView(customView)
                .setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->  })
                .setPositiveButton("Edit", DialogInterface.OnClickListener { dialog, which ->
                    mNama = customView.edit_nama.text.toString()
                    mNoHp = customView.edit_noHp.text.toString()
                    mEmail = customView.edit_email.text.toString()

                    when {
                        mNama.isEmpty() -> customView.edit_nama.setError("Nama tidak boleh kosong !!")
                        mNoHp.isEmpty() -> customView.edit_noHp.setError("No HP tidak boleh kosong !!")
                        mEmail.isEmpty() -> customView.edit_email.setError("Email tidak boleh kosong !!")

                        else -> {
                            editProfil()
                        }
                    }

                })
                .show()

        }

        pengaturan_passwordBaru.setOnClickListener {
            val customView: View = layoutInflater.inflate(R.layout.custom_dialog_password, null)

            val diaog : AlertDialog? = AlertDialog.Builder(this)
                .setTitle("Ganti Password")
                .setView(customView)
                .setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->  })
                .setPositiveButton("Selesai", DialogInterface.OnClickListener { dialog, which ->
                    mPassword = customView.password_baru.text.toString()

                    when{
                        mPassword.isEmpty() -> customView.password_baru.setError("Password tidak boleh kosong !!")

                        else -> {
                            editPassword()
                        }
                    }
                })
                .show()
        }
    }

    private fun editPassword()
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL.rest)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.editPassword(mPassword, idUser)
        val dialog = ProgressDialog(this)
        dialog.setMessage("Load User From Server...")
        dialog.show()
        call.enqueue(object : Callback<Value>{
            override fun onFailure(call: Call<Value>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(this@PengaturanActivity, "Terjadi Kesalahan : "+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                val res = response.body() as Value
                if(res != null) {
                    dialog.dismiss()
                    Toast.makeText(this@PengaturanActivity, res.message, Toast.LENGTH_SHORT).show()
                    loadUser()
                }
            }

        })
    }

    private fun editProfil()
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL.rest)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.editProfil(mNama, mEmail, mNoHp, idUser)
        val dialog = ProgressDialog(this)
        dialog.setMessage("Load User From Server...")
        dialog.show()
        call.enqueue(object : Callback<Value> {
            override fun onFailure(call: Call<Value>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(this@PengaturanActivity, "Terjadi Kesalahan : "+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                val res = response.body() as Value
                if(res != null) {
                    dialog.dismiss()
                    Toast.makeText(this@PengaturanActivity, res.message, Toast.LENGTH_SHORT).show()
                    loadUser()
                }
            }

        })
    }

    private fun loadUser()
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL.rest)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.getUser(idUser)
        val dialog = ProgressDialog(this)
        dialog.setMessage("Load User From Server...")
        dialog.show()
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(this@PengaturanActivity, "Terjadi Kesalahan : "+t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body() as User
                if (res != null){
                    if (!res.isError){
                        mNama = res.namaUser.toString()
                        mNoHp = res.hpUser.toString()
                        mEmail = res.emailUser.toString()
                    }
                    dialog.dismiss()

                    usernameTextView.setText(mNama)
                    emailTextView.setText(mEmail)

                    val foto = res.fotoUser
                    Picasso.with(this@PengaturanActivity)
                        .load(URL.fotoProfil + foto)
                        .into(profileCircleImageView)

                    Picasso.with(this@PengaturanActivity).isLoggingEnabled = true

                }
            }
        })

    }

    private fun LogoutAct()
    {
        val preference = Preferences(this)
        preference.clear()
        val intent = Intent(this@PengaturanActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}