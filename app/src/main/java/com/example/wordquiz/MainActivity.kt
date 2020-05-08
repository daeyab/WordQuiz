package com.example.wordquiz

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.wordquiz.WordBook.addedWordList
import com.example.wordquiz.WordBook.nowAddWordList
import com.example.wordquiz.WordBook.wordList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.row.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),View.OnClickListener {
//메인 엑티비티

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
      //TODO("점수 추가되는 거 다음버튼이 아니라 눌렸을 때 바로 확인 / 로고 / 디자인 이쁘게 / 규칙설명 어떻게? ")

    }

    private fun init() {
        startBtn.setOnClickListener(this)
        questionCntDownBtn.setOnClickListener(this)
        questionCntUpBtn.setOnClickListener(this)
        favBtn.setOnClickListener(this)
        infoBtn.setOnClickListener(this)
        listBtn.setOnClickListener(this)
        readFile()
    }

    companion object {
        fun addit(w: Word, mode:Int=1){
            val list=when(mode){
                1->wordList
                2->addedWordList
                else->null
            }

            for (i in 0..list?.size!! -1) {
                if(w.eng == list[i].eng){
                    val oldkorval=list[i].kor.split("/ ")
                    val newkorval=w.kor.split("/ ")
                    for(str in newkorval){
                        if(!oldkorval.contains(str))
                            list[i].kor+="/ $str"
                    }
                    return
                }
            }
            when(mode){
                1->{
                    Log.d("dbdb","전체")
                    wordList.add(w)
                }
                2-> {
                    Log.d("dbdb","추가")
                    addedWordList.add(w)
                    wordList.add(w)
                }
            }
        }
        fun hasKorean(s:String):Boolean{
            //한글이 있는지 체크
            for(i in 0..s.length-1){
                if(s[i].toInt()>=128 && s[i]!='～'){
                    //     Log.d("dbdb",s[i].toString())
                    return true
                }
            }
            return false
        }
    }


    private fun readFile(){
        val scanner= Scanner(resources.openRawResource(R.raw.words))

        while(scanner.hasNextLine()){
            var eng=scanner.nextLine()
            var kor=scanner.nextLine()
            if(hasKorean(eng) || !hasKorean(kor)){
                Log.d("dbdb","이건 오류: "+eng+" "+kor)
                continue
            }
            addit(Word(eng,kor))
        }
        scanner.close()
        wordList.sortBy { word->word.eng }

//        Collections.shuffle(words)
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
            R.id.infoBtn->{
                toast("""
                    건국대학교
                    공과대학
                    컴퓨터공학부 
                    201411194 김대엽
                    daeyab@naver.com
                """.trimIndent())
            }
            R.id.startBtn->{
                startActivity<QuizActivity>(
                    "cnt" to questionCntTxt.text.toString().toInt()
                )
            }
            R.id.favBtn->{
                startActivity<FavoriteListActivity>()
            }
            R.id.listBtn->{
                val i=Intent(this,ListActivity::class.java)
                startActivity(i)
            }

        }
    }
}