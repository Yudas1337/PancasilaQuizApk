package com.yudas.pancasila_quiz_apk.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.yudas.pancasila_quiz_apk.R
import com.squareup.picasso.Picasso
import com.yudas.pancasila_quiz_apk.URL.fotoProfil
import com.yudas.pancasila_quiz_apk.retrofit.Ranking
import kotlinx.android.synthetic.main.adapter_ranking.view.*

class AdapterRanking(private val context: Context, private val results: List<Ranking>) : RecyclerView.Adapter<AdapterRanking.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_ranking, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.setData(result)

    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun setData(result: Ranking)
        {
            itemView.namaUser!!.text = result.namaUser  
            itemView.skornya!!.text = result.skorUser

            itemView.index!!.text = result.index

            itemView.index!!.visibility = View.GONE
            itemView.fotoPiala!!.visibility = View.VISIBLE
           if(result.index == "1"){
               loadImage(R.drawable.champ1)
           } else if(result.index == "2"){
               loadImage(R.drawable.champ2)
           } else if(result.index == "3"){
               loadImage(R.drawable.champ3)
           } else{
               itemView.index!!.visibility = View.VISIBLE
               itemView.fotoPiala!!.visibility = View.GONE
           }

            Picasso.with(context)
                .load(fotoProfil + result.fotoUser)
                .fit()
                .centerCrop()
                .into(itemView.foto)

            Picasso.with(context).isLoggingEnabled = true


        }

        fun loadImage(image: Int){
            Picasso.with(context)
                    .load(image)
                    .fit()
                    .centerCrop()
                    .into(itemView.fotoPiala)

            Picasso.with(context).isLoggingEnabled = true
        }


        override fun onClick(view: View) {


        }
    }
}
