package com.example.wordquiz

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(),View.OnClickListener {

    lateinit var adapter: WordAdapter
    var txtvisible=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        WordBook.wordList.sortBy { word->word.eng } //단어 정렬
        setViewLayout() //레이아웃 설정
        adapter= WordAdapter(WordBook.wordList) //어답터 설정
        wordListTxt.text="단어 목록 (${WordBook.wordList.size})"
        wordListRecyclerView.adapter=adapter //어답터 부착
      //  addWordBtn.setOnClickListener(this)
        searchWordBtn.setOnClickListener(this)
//        ListtoMainBtn.setOnClickListener(this)
        searchWordTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val txt=searchWordTxt.text.toString()
                val searchedList= mutableListOf<Word>()
                for(w in WordBook.wordList){
                    if(w.eng.contains(txt))
                        searchedList.add(w)
                }
                adapter=WordAdapter(searchedList)
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
           //     val i= Intent(this,MainActivity::class.java)
            //    startActivity(i)
            }
            R.id.addWordBtn->{
           //     val i= Intent(this,AddWordActivity::class.java)
            //    startActivity(i)
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
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}
