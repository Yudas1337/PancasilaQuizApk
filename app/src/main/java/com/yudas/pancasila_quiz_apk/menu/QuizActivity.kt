package com.yudas.pancasila_quiz_apk.menu

import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.adapter.AdapterSoal
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.yudas.pancasila_quiz_apk.retrofit.Result
import java.util.ArrayList

class QuizActivity : AppCompatActivity() {

    private var results: List<Result>? = ArrayList()
    private var viewAdapter: AdapterSoal? = null
    private var kondisi: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        ic_back.setOnClickListener {
            finish()
        }

            mulaiquiz.setOnClickListener {
            mulaiquiz!!.visibility = View.GONE
            kondisi = true
            recyclersoal?.visibility = View.VISIBLE
            ic_back!!.visibility = View.GONE
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(URL.rest)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.tampilsoal()
        call.enqueue(object : Callback<Value> {
            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                val value = response.body()?.value
                recyclersoal!!.visibility = View.GONE
                if (value == "1") {
                    results = response.body()?.result!!
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    recyclersoal!!.layoutManager = mLayoutManager
                    recyclersoal!!.itemAnimator = DefaultItemAnimator()
                    viewAdapter = results?.let { AdapterSoal(this@QuizActivity, it) }
                    recyclersoal!!.adapter = viewAdapter
                }
            }

            override fun onFailure(call: Call<Value>, t: Throwable) {
                Toast.makeText(this@QuizActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onBackPressed() {
        if(kondisi == true){
            AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Quiz akan dibatalkan jika anda kembali!")
                    .setPositiveButton("Iya") { dialog, which ->
                        finish()
                        Toast.makeText(this, "Kuis Dibatalkan!", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("Batal") { dialog, which ->
                        // do nothing
                    }
                    .show()
        } else{
            finish()
        }
    }
}