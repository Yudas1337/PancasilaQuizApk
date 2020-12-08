package com.yudas.pancasila_quiz_apk.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import com.yudas.pancasila_quiz_apk.BankSoal
import com.yudas.pancasila_quiz_apk.MainActivity
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.URL.gambarSoal
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
    internal var nilaiSoal: Int = 0
    private var skor = 0
    private var index = 0
    private lateinit var bankSoal: BankSoal
    internal var kondisi: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        bankSoal = BankSoal()
        layoutSoal!!.visibility = View.GONE
        relativeatas!!.visibility = View.GONE
        mulaiquiz.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Pastikan Device anda terhubung dengan Internet!")
                    .setPositiveButton("Mulai Quiz!") { dialog, which ->
                        mulaiquiz!!.visibility = View.GONE
                        ic_back!!.visibility = View.GONE
                        kondisi = true
                        layoutSoal!!.visibility = View.VISIBLE
                        relativeatas!!.visibility = View.VISIBLE
                        getSoal()
                        Toast.makeText(this, "Quiz Dimulai!", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("Batal") { dialog, which ->
                        // do nothing
                    }
                    .show()

        }

        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

            }
        }
        timer.start()

        ic_back.setOnClickListener {
            this@QuizActivity.finish()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(URL.rest)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.getSoal()
        call.enqueue(object: Callback<Value> {
            override fun onFailure(call: Call<Value>, t: Throwable) {
                Toast.makeText(this@QuizActivity,"Loading Data...", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                val data = response.body()
                    val question = data?.question
                    for (q in question!!){
                        bankSoal.setSoal(arrayListOf(q.isiSoal,q.fotoSoal,q.nilaiSoal))
                        bankSoal.setJawabanBenar(q.kunci_jwb)
                        bankSoal.setJawaban(arrayListOf(q.opsi_a, q.opsi_b, q.opsi_c, q.opsi_d))
                    }
            }

        })


        opsi_a.setOnClickListener {
            if (mJawabanBenar == "A"){
                skor += nilaiSoal
                skornya.setText(skor.toString())
                getSoal()
            }else{
                getSoal()
            }
        }
        opsi_b.setOnClickListener {
            if (mJawabanBenar == "B"){
                skor += nilaiSoal
                skornya.setText(skor.toString())
                getSoal()
            }else{
                getSoal()
            }
        }
        opsi_c.setOnClickListener {
            if (mJawabanBenar == "C"){
                skor += nilaiSoal
                skornya.setText(skor.toString())
                getSoal()
            }else{
                getSoal()
            }
        }
        opsi_d.setOnClickListener {
            if (mJawabanBenar == "D"){
                skor += nilaiSoal
                skornya.setText(skor.toString())
                getSoal()
            }else{
                getSoal()
            }
        }
    }

    override fun onBackPressed()
    {
        if(kondisi){
            AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Quiz akan Dibatalkan jika keluar!")
                    .setPositiveButton("Iya") { dialog, which ->
                        val intent = Intent(this@QuizActivity, MainActivity::class.java)
                        startActivity(intent)
                        this@QuizActivity.finish()
                        Toast.makeText(this, "Quiz Dibatalkan!", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("Batal") { dialog, which ->
                        // do nothing
                    }
                    .show()
        } else{
            this@QuizActivity.finish()
        }

    }

    fun getSoal() {
        val nomor = index + 1;
        if(index < bankSoal.soalLength()){
            txt_soal.setText(bankSoal.getSoal(index)[0].toString())
            val foto = bankSoal.getSoal(index)[1].toString()
            if(foto == ""){
                fotoSoal!!.visibility = View.GONE
            } else{
                Picasso.with(this)
                        .load(gambarSoal + foto)
                        .into(fotoSoal)

                Picasso.with(this).isLoggingEnabled = true
                fotoSoal!!.visibility = View.VISIBLE
            }

            opsi_a.setText(bankSoal.getJawaban(index)[0].toString())
            opsi_b.setText(bankSoal.getJawaban(index)[1].toString())
            opsi_c.setText(bankSoal.getJawaban(index)[2].toString())
            opsi_d.setText(bankSoal.getJawaban(index)[3].toString())
            judul.setText("Soal $nomor/${bankSoal.soalLength()}")
            mJawabanBenar = bankSoal.getJawabanBenar(index)!!
            nilaiSoal = bankSoal.getSoal(index)[2]!!.toInt()
            index++
        }
        else{
            Toast.makeText(this, "skor : "+ skor, Toast.LENGTH_LONG).show()
        }


    }




}

