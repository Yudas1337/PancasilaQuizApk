package com.yudas.pancasila_quiz_apk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yudas.pancasila_quiz_apk.retrofit.Result
import com.yudas.pancasila_quiz_apk.R
import com.yudas.pancasila_quiz_apk.URL.gambarSoal
import kotlinx.android.synthetic.main.adapter_soal.view.*

class AdapterSoal(private val context: Context, private val results: List<Result>) : RecyclerView.Adapter<AdapterSoal.ViewHolder>() {

   internal var limit: Int = 1
    internal var index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_soal, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[index]
        holder.setData(result)

    }

    override fun getItemCount(): Int {
        return limit
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun setData(result: Result)
        {
            itemView.isiSoal!!.text = result.isiSoal
            Picasso.with(context)
                    .load(gambarSoal + result.fotoSoal)
                    .into(itemView.fotoSoal)

            Picasso.with(context).isLoggingEnabled = true

            itemView.isiSoal.setOnClickListener {
                if(index == results.size - 1){
                    Toast.makeText(context, "ini soal terakhir!", Toast.LENGTH_SHORT).show()
                } else{
                    index++
                    notifyDataSetChanged()
                }

            }

        }
        override fun onClick(view: View) {
        }
    }
}
