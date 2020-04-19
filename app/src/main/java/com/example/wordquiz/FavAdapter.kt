package com.example.wordquiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fav_row.view.*

class FavAdapter(val lst:MutableList<Word>) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    //1.먼저상속
    //2.뷰홀더 생성 -> 넣어주고 뒤에 () 통해 호출

    interface OnItemClickListener{
        fun OnItemClick(
            holder: FavViewHolder,
            view:View,
            data:Word, //이게 뭘지 다시 생각해봐
            position: Int
        )
    }
    inner class FavViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //안의 내용물
        val engTxt=itemView.findViewById<TextView>(R.id.FavEngTxt)
        val korTxt=itemView.findViewById<TextView>(R.id.FavKorTxt)

        var itemClickListener:OnItemClickListener?=null
        init {
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(
                    this,
                    it,
                    lst[adapterPosition],
                    adapterPosition
                )
                if(itemView.FavKorTxt.visibility==View.GONE)
                    itemView.FavKorTxt.visibility=View.VISIBLE
                else
                    itemView.FavKorTxt.visibility=View.GONE

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        //레이아웃 설정
        val v=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fav_row,parent,false)
        return FavViewHolder(v)
    } //이건 보통 같음 레아아웃만 살짜쿵 다르게

    override fun getItemCount(): Int {
        return lst.size
    } //이건 아예 똑같은듯

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.korTxt.text=lst[position].kor
        holder.engTxt.text=lst[position].eng
    }

    fun moveItem(oldPos:Int, newPos:Int){
        //위치 바꿔주는 것
        val item=lst.get(oldPos)
        lst.removeAt(oldPos)
        lst.add(newPos,item)
        notifyItemMoved(oldPos,newPos)
    }

    //스와이프 지우기
    fun removeItem(pos:Int){
        lst.removeAt(pos)
        notifyItemRemoved(pos)
    }
}