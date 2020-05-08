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
import com.example.wordquiz.WordBook.favWordList
import com.example.wordquiz.WordBook.wordList
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_show_result.*
import kotlinx.android.synthetic.main.activity_show_result.favRecyclerView
import kotlinx.android.synthetic.main.activity_show_result.toMainBtn
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ListActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var adapter: FavAdapter
    var txtvisible=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        init()

    }

    private fun init() {
        wordList.sortBy { word->word.eng } //단어 정렬
        setViewLayout() //레이아웃 설정
        adapter= FavAdapter(WordBook.wordList) //어답터 설정
        wordListRecyclerView.adapter=adapter //어답터 부착
        wordListTxt.text="단어 목록 (${WordBook.wordList.size})"
        addWordBtn.setOnClickListener(this)
        searchWordBtn.setOnClickListener(this)
        ListtoMainBtn.setOnClickListener(this)
        searchWordTxt.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val txt=searchWordTxt.text.toString()
                val searchedList= mutableListOf<Word>()
                for(w in wordList){
                    if(w.eng.contains(txt))
                        searchedList.add(w)
                }
                adapter=FavAdapter(searchedList)
                wordListRecyclerView.adapter=adapter //어답터 부착
                wordListTxt.text="단어 목록 (${searchedList.size})"

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ListtoMainBtn->{
                val i=Intent(this,MainActivity::class.java)
                startActivity(i)
            }
            R.id.addWordBtn->{
                val i=Intent(this,AddWordActivity::class.java)
                startActivity(i)
            }
            R.id.searchWordBtn->{
                if(txtvisible==false){
                    txtvisible=true
                    searchWordTxt.visibility=View.VISIBLE
                }
                else{
                    txtvisible=false
                    searchWordTxt.visibility=View.GONE
                }
            }
        }
    }


    private fun setViewLayout() { //뷰레이아웃설정
        wordListRecyclerView.layoutManager= LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

}



