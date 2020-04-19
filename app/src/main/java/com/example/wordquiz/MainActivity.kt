package com.example.wordquiz

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.row.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),View.OnClickListener {

    var words= mutableListOf<Word>()
    var candidates= mutableListOf<Word>()

    var array=ArrayList<String>()
    lateinit var adapter: MyAdapter
    lateinit var tts:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
      //  TODO("점수 추가되는 거 다음버튼이 아니라 눌렸을 때 바로 확인 / 로고 / 디자인 이쁘게 / 규칙설명 어떻게? ")

    }

    private fun init() {
        startBtn.setOnClickListener(this)
        questionCntDownBtn.setOnClickListener(this)
        questionCntUpBtn.setOnClickListener(this)
        favBtn.setOnClickListener(this)
        addBtn.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.questionCntDownBtn->{
                val cnt=questionCntTxt.text.toString().toInt()
                if((cnt>1) && (cnt<21))
                    questionCntTxt.text=(cnt-1).toString()
                else
                    toast("값 범위 (1~20)")
            }
            R.id.questionCntUpBtn->{
                val cnt=questionCntTxt.text.toString().toInt()
                if((cnt>0) && (cnt<20))
                    questionCntTxt.text=(cnt+1).toString()
                else
                    toast("값 범위 (1~20)")
            }
        //    R.id.questionCntTxt->{ }
            R.id.addBtn->{ }
            R.id.startBtn->{
                startActivity<QuizActivity>(
                    "cnt" to questionCntTxt.text.toString().toInt()
                )
            }
            R.id.favBtn->{
                startActivity<FavoriteListActivity>()
                toast("즐착")
            }

        }
    }
}