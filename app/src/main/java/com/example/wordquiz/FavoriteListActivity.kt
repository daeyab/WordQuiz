package com.example.wordquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordquiz.WordBook.favWordList
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.activity_show_result.favRecyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class FavoriteListActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var adapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        init()

    }

    private fun init() {
        setViewLayout() //레이아웃 설정
        adapter= WordAdapter(WordBook.favWordList) //어답터 설정
        favRecyclerView.adapter=adapter //어답터 부착
        swipeActions()

     //   favListTxt.text="즐겨찾기 목록 (${WordBook.favWordList.size})"
    //    favdeleteBtn.setOnClickListener(this)
     //   favquestionBtn.setOnClickListener(this)
      //  favtoMainBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
    //    when(v?.id){
  //          R.id.favdeleteBtn->{
       //         favWordList.clear()
      //          toast("즐겨찾기 목록이 삭제되었습니다")
     //           startActivity<MainActivity>()
          //  }
     //       R.id.favquestionBtn->{
        //        toast("""
         //           [삭제] : 우로 밀기
          //          [보기] : 클릭
           //     """.trimIndent())
          //  }
          //  R.id.favtoMainBtn->{
           //     startActivity<MainActivity>()
          //  }
       // }
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
      //          adapter.moveItem(viewHolder.adapterPosition,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
         //           adapter.removeItem(viewHolder.adapterPosition)
     //               favListTxt.text="즐겨찾기 목록 (${WordBook.favWordList.size})"
            }
        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
  //      itemTouchHelper.attachToRecyclerView(favRecyclerView)
        //부착
    }

    private fun setViewLayout() { //뷰레이아웃설정
    //    favRecyclerView.layoutManager= LinearLayoutManager(
 //           this,
        //    LinearLayoutManager.VERTICAL,
     ////       false
    //    )
    }
}