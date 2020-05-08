package com.example.wordquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordquiz.WordBook.addedWordList
import com.example.wordquiz.WordBook.favWordList
import com.example.wordquiz.WordBook.wordList
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_show_add_words.*
import kotlinx.android.synthetic.main.activity_show_result.*
import kotlinx.android.synthetic.main.activity_show_result.favRecyclerView
import kotlinx.android.synthetic.main.activity_show_result.toMainBtn
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ShowAddWordsActivity : AppCompatActivity() {

    lateinit var adapter: FavAdapter
    var txtvisible=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_add_words)
        init()

    }

    private fun init() {
        addedWordList.sortBy { word->word.eng } //단어 정렬
        setViewLayout() //레이아웃 설정
        adapter= FavAdapter(WordBook.addedWordList) //어답터 설정
        addedWordListRecyclerView.adapter=adapter //어답터 부착
        addedListTxt.text="추가된 단어 목록 (${WordBook.addedWordList.size})"
        ShowToAddBtn.setOnClickListener {
            val i=Intent(this,AddWordActivity::class.java)
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



