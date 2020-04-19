package com.example.wordquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.activity_show_result.*
import kotlinx.android.synthetic.main.activity_show_result.favRecyclerView
import kotlinx.android.synthetic.main.activity_show_result.toMainBtn
import org.jetbrains.anko.startActivity

class FavoriteListActivity : AppCompatActivity() {

    lateinit var adapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        init()
        toMainBtn.setOnClickListener {
            startActivity<MainActivity>()
        }

    }

    private fun init() {
        setViewLayout() //레이아웃 설정
        adapter= FavAdapter(WordBook.favWordList) //어답터 설정
        favRecyclerView.adapter=adapter //어답터 부착
        swipeActions()

        favListTxt.text="즐겨찾기 목록 (${WordBook.favWordList.size})"
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
                    adapter.removeItem(viewHolder.adapterPosition)
                    favListTxt.text="즐겨찾기 목록 (${WordBook.favWordList.size})"
            }
        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
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