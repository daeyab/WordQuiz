package com.example.wordquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordquiz.WordBook.favWordList
import com.example.wordquiz.WordBook.nowAddWordList
import kotlinx.android.synthetic.main.activity_show_result.*
import org.jetbrains.anko.startActivity

class ShowResultActivity : AppCompatActivity() {

    lateinit var adapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)
        val perfectScore=(intent.getIntExtra("totalInning",0)*2).toString()
        val totalScore=intent.getIntExtra("totalScore",0).toString()
        val totalCorrected=intent.getIntExtra("totalCorrected",0).toString()

        resultscore.text="$totalScore 점 / $perfectScore 점"
        resultCorrected.text="${perfectScore.toInt()/2} 문제 중 $totalCorrected 정답"

        init()


    }

    private fun init() {
        setViewLayout() //레이아웃 설정
        adapter= FavAdapter(nowAddWordList) //어답터 설정
        favRecyclerView.adapter=adapter //어답터 부착
        swipeActions()
        resultAddTxt.text="즐겨찾기 추가 (${nowAddWordList.size})"
        toMainBtn.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

    private fun swipeActions() {
        val simpleCallback=object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //    adapter.removeItem(viewHolder.adapterPosition) 여기서는 스와이프 기능 삭제
            }
        }
        val itemTouchHelper=ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(favRecyclerView)
        //부착
    }

    private fun setViewLayout() { //뷰레이아웃설정
        favRecyclerView.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

}
