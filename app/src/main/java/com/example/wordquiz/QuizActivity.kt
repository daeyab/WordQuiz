package com.example.wordquiz

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordquiz.R.color
import com.example.wordquiz.R.drawable.buttonbackground
import com.example.wordquiz.WordBook.favWordList
import com.example.wordquiz.WordBook.nowAddWordList
import com.example.wordquiz.WordBook.wordList
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.row.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList


class QuizActivity : AppCompatActivity(),View.OnClickListener {



    var words= mutableListOf<Word>()
    lateinit var adapter: QuizAdapter
    lateinit var tts:TextToSpeech
    var isTTSready=false
    var wordIndex=0
    var totalInning:Int=0
    var totalCorrected:Int=0
    var nowInning:Int=1
    var totalSocre:Int=0
    var watchedWord=false
    var arr=mutableListOf<Word>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        totalInning=intent.getIntExtra("cnt",0)
        //전달 받음
     //TODO 이거횟수제한

        init()
    }

    private fun init() {
        nowAddWordList.clear() //이번 게임 추가할 할목들
        readFile() //파일 읽어오기
        setupTTS() //단어읽기 세팅
        setViewLayout() //뷰레이아웃설정
        showNext()//다음 문제 출제

        listenAgainBtn.setOnClickListener(this)
        nextQuizBtn.setOnClickListener(this)
        showWordBtn.setOnClickListener(this)
        quizhelpbtn.setOnClickListener(this)
        goBackMainBtn.setOnClickListener(this)
    }

    private fun setViewLayout() { //뷰레이아웃설정
        wordRecyclerView.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    //단어를 읽는 함수*
    //단어의 중복을 막기위해 시간이 걸리더라도 모든 단어 필터링하게 설정
    //같은 영어단어가 나올 시 한글뜻을 "/ "기준으로 분리하여
    //기존 한글 또한 같은 방법으로 분리하여 중복을 체크함
    //중복되지 않은 부분만 한글 string 뒤에 부착
    //셔플
    private fun readFile(){
       words= wordList
        Collections.shuffle(words)
    }

    private fun setupTTS(){
        tts=TextToSpeech(
            this,TextToSpeech.OnInitListener {
                        isTTSready=true
                        tts.language=Locale.US
                tts.speak(quizWord.text.toString(),TextToSpeech.QUEUE_ADD,null,null)
            }
        )
    }

    private fun showNext(){
        nextQuizBtn.isEnabled=false
        if(nowInning>totalInning){ //전체 문제를 다 봤을 경우
            favWordList.addAll(nowAddWordList)
            startActivity<ShowResultActivity>("totalScore" to totalSocre, "totalInning" to totalInning,"totalCorrected" to totalCorrected)
        }
        else{
            watchedWord=false
            var idx=1
            //문제 5개 출제할 때 2차 중복 체크
            val checkDuplicated= mutableSetOf<String>()
                arr.clear()
            while(idx<=5){
                if(words[wordIndex].eng in checkDuplicated)
                    wordIndex++
                else{
                    checkDuplicated.add(words[wordIndex].eng)
                    arr.add(words[wordIndex])
                    idx++
                }
            }
            val ansIndex=(0..4).random()
            quizWord.text=arr[ansIndex].eng
            adapter= QuizAdapter(arr) //어댑터에 퀴즈 어댑터 연결 / 보기 인자로 전달
            wordRecyclerView.adapter=adapter //어댑터 부착
            wordIndex+=5 //5개씩 섞어가며 출제
            setupAdapterListener(ansIndex)
            if(nowInning!=1 && isTTSready)
                tts.speak(quizWord.text.toString(),TextToSpeech.QUEUE_ADD,null,null)
            quizWord.visibility=View.GONE
            inningTxt.text="$nowInning/$totalInning"
            nowInning++

        }

    }


    private fun setupAdapterListener(ansIndex:Int){
        var checked=false //문제가 체크되었는지
        adapter.itemListener=object:QuizAdapter.OnItemListener{ //문제가 클릭되었을 때
            override fun OnItemClick(
                holder: QuizAdapter.QuizViewHolder,
                view: View,
                wordTxtView: TextView, //여기서 클릭된 뷰
                position: Int
            ) { //클릭 이벤트 처리
                if(checked==false ){//첫 클릭이 실행되었을 때

                    for(i in 0..4){
                        wordRecyclerView[i].candidateWords.text=arr[i].eng+" : "+arr[i].kor
                        wordRecyclerView[i].favCheck.visibility=View.VISIBLE
                    } //리사이클러뷰의 값들을 보여줌
                    nextQuizBtn.isEnabled=true
                    if(ansIndex==position){ //정답인 경우
                        totalCorrected++
                        wordTxtView.backgroundDrawable=getDrawable(R.drawable.answordbg)
                        wordTxtView.setTextColor(resources.getColor(R.color.mywhite))

                        if(watchedWord)
                            totalSocre++
                        else
                            totalSocre+=2
                    }
                    else { //틀렸을 경우
                        view.backgroundDrawable=getDrawable(R.drawable.buttonbackground)
                        wordTxtView.backgroundDrawable=getDrawable(R.drawable.wrongwordbg)
                        wordTxtView.setTextColor(resources.getColor(R.color.mywhite))
                        wordRecyclerView[ansIndex].candidateWords.setTextColor(resources.getColor(R.color.mywhite))
                        wordRecyclerView[ansIndex].candidateWords.backgroundDrawable=getDrawable(R.drawable.answordbg)
                        nowAddWordList.add(arr[position])
                        wordRecyclerView[position].favCheck.isChecked=checked
                        wordRecyclerView[position].favCheck.isEnabled=false
                    }
                    checked=true
                    scoreTxt.text="점수\n$totalSocre 점"

                }
                else{
                    toast("이미 체크하셨습니다.")
                }
            }
        }
    }
    override fun onClick(v: View?) {
       when(v?.id){
           R.id.listenAgainBtn->{
               if(isTTSready)
                   tts.speak(quizWord.text.toString(),TextToSpeech.QUEUE_ADD,null,null)
           }
           R.id.nextQuizBtn->{
               showNext()
           }
           R.id.showWordBtn->{
               quizWord.visibility=View.VISIBLE
               watchedWord=true
            }
           R.id.quizhelpbtn->{
                toast("""
                    1. 단어를 보지 않고 맞추면 +2점
                    2. 단어를 보고 맞추면 +1점
                    3. 다시 듣기는 무제한 가능
                    4. 문제가 끝난뒤 즐겨찾기 추가 가능
                    5. 틀린 문제는 자동으로 추가
                """.trimIndent())
           }
           R.id.goBackMainBtn->{
               startActivity<MainActivity>()
           }
       }
    }
}
