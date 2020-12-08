package com.yudas.pancasila_quiz_apk.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.yudas.pancasila_quiz_apk.adapter.AdapterRanking
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL
import com.yudas.pancasila_quiz_apk.retrofit.Functions
import com.yudas.pancasila_quiz_apk.retrofit.Value
import kotlinx.android.synthetic.main.activity_ranking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import com.yudas.pancasila_quiz_apk.retrofit.Ranking

class RankingActivity : AppCompatActivity() {

    private var results: List<Ranking>? = ArrayList()
    private var viewAdapter: AdapterRanking? = null

    private var shimmer_modules: ShimmerFrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        shimmer_modules = findViewById(R.id.shimmer_modules)

        ic_back.setOnClickListener {

            finish()
        }

        val searchmodules = findViewById<View>(R.id.searchmodules) as SearchView
        searchmodules.isIconified = false
        searchmodules.queryHint = "Cari Pengguna"

        searchmodules.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {

                val retrofit = Retrofit.Builder()
                        .baseUrl(URL.rest)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val api = retrofit.create<Functions>(Functions::class.java)
                val call = api.searchrank(newText)
                call.enqueue(object : Callback<Value> {
                    override fun onResponse(call: Call<Value>, response: Response<Value>) {
                        val value = response.body()?.value
                        recyclermodules!!.visibility = View.GONE
                        if (value == "1") {

                            shimmer_modules?.stopShimmer()
                            shimmer_modules?.clearAnimation()
                            shimmer_modules?.visibility = View.GONE
                            recyclermodules?.visibility = View.VISIBLE

                            results = response.body()?.ranking
                            val mLayoutManager = LinearLayoutManager(applicationContext)
                            recyclermodules!!.layoutManager = mLayoutManager
                            recyclermodules!!.itemAnimator = DefaultItemAnimator()
                            viewAdapter = results?.let { AdapterRanking(this@RankingActivity, it) }
                            recyclermodules!!.adapter = viewAdapter
                        }
                    }

                    override fun onFailure(call: Call<Value>, t: Throwable) {
                        Toast.makeText(this@RankingActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                    }
                })

                return true
            }

            override fun onQueryTextChange(newText: String ): Boolean {


                return false
            }
        })



        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.refreshmodules)
        pullToRefresh.setOnRefreshListener {
            loadDataApps()
            Toast.makeText(this@RankingActivity, "Refreshing Data..", Toast.LENGTH_SHORT).show()
            pullToRefresh.isRefreshing = false
        }


        loadDataApps()
        shimmer_modules?.startShimmer()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        loadDataApps()
    }

    private fun loadDataApps() {
            val retrofit = Retrofit.Builder()
                    .baseUrl(URL.rest)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.listrank()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value

                    recyclermodules?.visibility = View.GONE
                    if (value == "1") {

                        shimmer_modules?.stopShimmer()
                        shimmer_modules?.clearAnimation()
                        shimmer_modules?.visibility = View.GONE
                        recyclermodules?.visibility = View.VISIBLE

                        results = response.body()?.ranking
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclermodules!!.layoutManager = mLayoutManager
                        recyclermodules!!.itemAnimator = DefaultItemAnimator()
                        viewAdapter = results?.let { AdapterRanking(this@RankingActivity, it) }

                        recyclermodules!!.adapter = viewAdapter



                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(this@RankingActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        return true
    }
}
