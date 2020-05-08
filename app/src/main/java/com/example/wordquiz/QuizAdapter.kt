package com.example.wordquiz

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordquiz.WordBook.favWordList
import com.example.wordquiz.WordBook.nowAddWordList
import kotlinx.android.synthetic.main.row.view.*

class QuizAdapter(val candidates:MutableList<Word>)
    :RecyclerView.Adapter<QuizAdapter.QuizViewHolder>(){

    interface OnItemListener{
        fun OnItemClick(
            holder:QuizViewHolder,
            view:View,
            txtView:TextView,
            position: Int)

    }
    var itemListener:OnItemListener?=null

    inner class QuizViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val meaningtxt = itemView.findViewById<TextView>(R.id.candidateWords)
        init {
            itemView.setOnClickListener {
                Log.d("dbdb",itemView.javaClass.toString())
                  itemListener?.OnItemClick(
                    this,
                    it,
                    meaningtxt,//글자를 눌렀을 때
                    adapterPosition
                  )
            }
            itemView.favCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                val str=itemView.candidateWords.text.toString()
                Log.d("dbdb",str.toString()+"str")
                val wordArr=str.split(" : ")
                val w=Word(wordArr[0],wordArr[1])
                if(isChecked){
                    Log.d("dbdb",w.toString()+"추가")
                    nowAddWordList.add(w)
                }
                else{
                    Log.d("dbdb",w.toString()+"삭제")
                    nowAddWordList.remove(w)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : QuizViewHolder{
        val v=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row,parent,false)
        return QuizViewHolder(v)
    }

    override fun getItemCount(): Int { //보여지는 거 갯수 출력
        return candidates.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder,position:Int){
        holder.meaningtxt.text=candidates[position].kor //한국말 출력부분
        holder.meaningtxt.setBackgroundResource(R.drawable.wordbackground)
    }



}