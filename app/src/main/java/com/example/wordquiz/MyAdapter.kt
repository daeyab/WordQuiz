package com.example.wordquiz

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

class MyAdapter(val candidates:MutableList<Word>)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    interface OnItemListener{
        fun OnItemClick(
            holder:MyViewHolder,
            view:View,
            txtView:TextView,
            position: Int)

    }
    var itemListener:OnItemListener?=null
    var wordClicked=false

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val item=itemView
        val meaningtxt = itemView.findViewById<TextView>(R.id.candidateWords)
        val favChecked = itemView.findViewById<CheckBox>(R.id.favCheck)

        init {
            var checked=false
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
                    favWordList.add(w)
                    nowAddWordList.add(w)
                }
                else{
                    Log.d("dbdb",w.toString()+"삭제")
                    favWordList.remove(w)
                    nowAddWordList.remove(w)
                }
            }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MyViewHolder{
        val v=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int { //보여지는 거 갯수 출력
        return candidates.size
    }

    override fun onBindViewHolder(holder: MyViewHolder,position:Int){
        holder.meaningtxt.text=candidates[position].kor //한국말 출력부분
      //  holder.favChecked.setBackgroundColor(Color.CYAN) //한국말 출력부분
    }

    fun setFavWords() {
    //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}