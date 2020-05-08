package com.example.wordquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.wordquiz.WordBook.addedWordList
import com.example.wordquiz.WordBook.wordList
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.activity_list.*
import org.jetbrains.anko.toast

class AddWordActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        init()
    }


    private fun init() {
        confirmAddBtn.setOnClickListener(this)
        cancelAddBtn.setOnClickListener(this)
        showAddBtn.setOnClickListener(this)
        addWordEngTxt.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
              val len=  addWordEngTxt.text.toString().length
                if(len>0) confirmAddBtn.isEnabled=true
                else confirmAddBtn.isEnabled=false
              var hasProblem=false
                for(w in wordList){
                    if(w.eng==addWordEngTxt.text.toString()){
                        textInputLayout.error="${w.kor}"
                        hasProblem=true
                    }
                }
                if(!hasProblem){
                    textInputLayout.error=null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            }
        )
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.confirmAddBtn-> {
                val eng=addWordEngTxt.text.toString()
                val kor=addWordKorTxt.text.toString()
                if(MainActivity.hasKorean(eng) || !MainActivity.hasKorean(kor) || kor==""){
                    Log.d("dbdb","이건 오류: "+eng+" "+kor)
                    toast("단어가 정상적으로 저장되지 않았습니다.")
                }
                else{
                    toast("단어가 정상적으로 저장되었습니다.")
                    MainActivity.addit(Word(eng,kor),mode=2) //추가목록 추가
                }
                addWordEngTxt.setText("")
                addWordKorTxt.setText("")
                //     val i=Intent(this,ListActivity::class.java)
                //     startActivity(i)
                Log.d("dbdb", addedWordList.toString())
            }
            R.id.cancelAddBtn-> {
                val i= Intent(this,ListActivity::class.java)
                startActivity(i)
            }
            R.id.showAddBtn->{
                val i=Intent(this,ShowAddWordsActivity::class.java)
                startActivity(i)
            }

        }
    }
}
