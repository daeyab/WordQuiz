package com.example.wordquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordquiz.WordBook.addedWordList
import kotlinx.android.synthetic.main.activity_show_add_words.*

class ShowAddWordsActivity : AppCompatActivity() {

    lateinit var adapter: WordAdapter
    var txtvisible=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_add_words)
        init()

    }

    private fun init() {
        addedWordList.sortBy { word->word.eng } //단어 정렬
        setViewLayout() //레이아웃 설정
        adapter= WordAdapter(WordBook.addedWordList) //어답터 설정
        addedWordListRecyclerView.adapter=adapter //어답터 부착
        addedListTxt.text="추가된 단어 목록 (${WordBook.addedWordList.size})"
        ShowToAddBtn.setOnClickListener {
            val i=Intent(this,LaunchActivity::class.java)
            i.putExtra("GO","ADD")
            startActivity(i)
        }
    }

    private fun setViewLayout() { //뷰레이아웃설정
        addedWordListRecyclerView.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

}



