package com.yudas.pancasila_quiz_apk.menu

import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.os.Bundle
import android.widget.Toast
import com.yudas.pancasila_quiz_apk.BankSoal
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizActivity : AppCompatActivity() {

    private lateinit var mJawabanBenar: String
    private var skor = 0
    private var index = 0
    private lateinit var bankSoal: BankSoal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        bankSoal = BankSoal()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL.rest)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.getSoal()
        call.enqueue(object: Callback<Value> {
            override fun onFailure(call: Call<Value>, t: Throwable) {
                Toast.makeText(this@QuizActivity,"error: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                val data = response.body()
                    val question = data?.question
                    for (q in question!!){
                        bankSoal.setSoal(q.isiSoal)
                        bankSoal.setJawabanBenar(q.kunci_jwb)
                        bankSoal.setJawaban(arrayListOf(q.opsi_a, q.opsi_b, q.opsi_c, q.opsi_d))
                    }
                getSoal()
            }

        })


        opsi_a.setOnClickListener {
            if (mJawabanBenar == "A"){
                skor += 1
                getSoal()
            }else{
                getSoal()
            }
        }
        opsi_b.setOnClickListener {
            if (mJawabanBenar == "B"){
                skor += 1
                getSoal()
            }else{
                getSoal()
            }
        }
        opsi_c.setOnClickListener {
            if (mJawabanBenar == "C"){
                skor += 1
                getSoal()
            }else{
                getSoal()
            }
        }
        opsi_d.setOnClickListener {
            if (mJawabanBenar == "D"){
                skor += 1
                getSoal()
            }else{
                getSoal()
            }
        }
    }

    fun getSoal() {
        if(index < bankSoal.soalLength()){
            txt_soal.setText(bankSoal.getSoal(index))
            opsi_a.setText(bankSoal.getJawaban(index)?.get(0).toString())
            opsi_b.setText(bankSoal.getJawaban(index)?.get(1).toString())
            opsi_c.setText(bankSoal.getJawaban(index)?.get(2).toString())
            opsi_d.setText(bankSoal.getJawaban(index)?.get(3).toString())
            mJawabanBenar = bankSoal.getJawabanBenar(index)!!
            index++
        }
        else{
            Toast.makeText(this, "skor : "+ skor, Toast.LENGTH_LONG).show()
        }


    }




}

