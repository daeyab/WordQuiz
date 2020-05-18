package com.example.wordquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordquiz.WordBook.favWordList
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavFragment : Fragment(),View.OnClickListener {
    lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    fun init(){
        setViewLayout() //레이아웃 설정
        adapter= WordAdapter(WordBook.favWordList) //어답터 설정
        favListRecyclerView.adapter=adapter //어답터 부착
        swipeActions()

        favListTxt.text="즐겨찾기 목록 (${WordBook.favWordList.size})"
        favdeleteWordBtn.setOnClickListener(this)
        favsearchWordBtn.setOnClickListener(this)
        //favquestionBtn.setOnClickListener(this)
        //favtoMainBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.favdeleteWordBtn->{
                favWordList.clear()
               //TODO(아직은 전체 삭제인데 선택 삭제로 변경해여 함
            }
            R.id.favsearchWordBtn->{
                //TODO(즐찾 단어 검색)
            }

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
                adapter.removeItem(viewHolder.adapterPosition)
                favListTxt.text="즐겨찾기 목록 (${WordBook.favWordList.size})"
            }
        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(favListRecyclerView)
        //부착
    }

    private fun setViewLayout() { //뷰레이아웃설정
        favListRecyclerView.layoutManager= LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

}
