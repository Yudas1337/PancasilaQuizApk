package com.yudas.pancasila_quiz_apk.menu

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
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
import com.yudas.pancasila_quiz_apk.auth.LoginActivity
import com.yudas.pancasila_quiz_apk.auth.Preferences
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_main.*
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
    private var hitung = 15
    private var minus = 0

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        bankSoal = BankSoal()
        layoutSoal!!.visibility = View.GONE
        relativeatas!!.visibility = View.GONE
        mulaiquiz!!.visibility = View.GONE

        mulaiquiz.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Pastikan Device anda terhubung dengan Internet!")
                    .setPositiveButton("Mulai Quiz") { dialog, which ->
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

        timer = object: CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                hitung -= 1
                if(hitung > 3){
                    minus = nilaiSoal / hitung
                    nilaiSoal -= minus
                } else{
                    // error handler divided by zero! jgn dihapus
                }
                waktunya.setText(hitung.toString())
            }

            override fun onFinish() {
                getSoal()
            }
        }




        ic_back.setOnClickListener {
            this@QuizActivity.finish()
        }


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

        fetchsoal()

    }

    override fun onBackPressed()
    {
        if(kondisi){
            AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Anda harus menyelesaikan kuis terlebih dahulu!")
                    .setPositiveButton("Ok") { dialog, which ->
                    }
                    .show()
        } else{
            this@QuizActivity.finish()
        }

    }

    private fun fetchsoal(){
        val retrofit = Retrofit.Builder()
                .baseUrl(URL.rest)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.getSoal()
        val dialog = ProgressDialog(this)
        dialog.setMessage("Fetch Soal From Server...")
        dialog.show()
        call.enqueue(object: Callback<Value> {
            override fun onFailure(call: Call<Value>, t: Throwable) {
                dialog.dismiss()
                AlertDialog.Builder(this@QuizActivity)
                        .setTitle("Peringatan")
                        .setMessage("Tidak dapat Load Soal! Pastikan Koneksi Internet Menyala dan Stabil!")
                        .setPositiveButton("Muat Ulang") { dialog, which ->
                            fetchsoal()
                        }.setNegativeButton("Batal") { dialog, which ->
                            val intent = Intent(this@QuizActivity, MainActivity::class.java)
                            startActivity(intent)
                            this@QuizActivity.finish()
                        }
                        .show()
            }

            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                Toast.makeText(this@QuizActivity, "Berhasil Load Soal", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                mulaiquiz!!.visibility = View.VISIBLE
                val data = response.body()
                val question = data?.question
                for (q in question!!){
                    bankSoal.setSoal(arrayListOf(q.isiSoal,q.fotoSoal,q.nilaiSoal))
                    bankSoal.setJawabanBenar(q.kunci_jwb)
                    bankSoal.setJawaban(arrayListOf(q.opsi_a, q.opsi_b, q.opsi_c, q.opsi_d))
                }
            }

        })
    }

    private fun getSoal() {
        hitung = 15
        timer.cancel()
        timer.start()
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
            timer.cancel()

            storeAkhir()
        }


    }


    private fun storeAkhir()
    {
        val preference = Preferences(this)
        val idUser = preference.getInt("STATUS_LOGIN",0)
        val dialog = ProgressDialog(this)
        dialog.setMessage("Loading Skor Akhir..")
        dialog.show()
        val retrofit = Retrofit.Builder()
                .baseUrl(URL.rest)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create<Functions>(Functions::class.java)
        val call = api.ranking(idUser.toString(),skor.toString())
        call.enqueue(object : Callback<Value> {
            override fun onResponse(call: Call<Value>, response: Response<Value>) {
                val value = response.body() as Value
                if (value != null) {
                    if (!value.isError) {
                        val intent = Intent(this@QuizActivity, ResultActivity::class.java)
                        intent.putExtra("skor",skor.toString())
                        startActivity(intent)
                        this@QuizActivity.finish()
                    }
                    dialog.dismiss()
                    Toast.makeText(this@QuizActivity, value.message, Toast.LENGTH_SHORT).show()

                }
            }
            override fun onFailure(call: Call<Value>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(this@QuizActivity, "Mohon Periksa Koneksi Internet!", Toast.LENGTH_SHORT).show()
                storeAkhir()
            }
        })
    }




}

