package com.example.wordquiz

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordquiz.WordBook.favWordList
import com.example.wordquiz.WordBook.nowAddWordList
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.row.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList


class QuizActivity : AppCompatActivity(),View.OnClickListener {



    var words= mutableListOf<Word>()
    lateinit var adapter: MyAdapter
    lateinit var tts:TextToSpeech
    var isTTSready=false
    var wordIndex=0
    var totalInning:Int=0
    var nowInning:Int=1
    var totalSocre:Int=0
    var watchedWord=false
    var arr=mutableListOf<Word>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        totalInning=intent.getIntExtra("cnt",0)
     //TODO 이거횟수제한

        init()
    }

    private fun init() {
        nowAddWordList.clear()
        readFile()
        setupTTS()
        setViewLayout()
        showNext()
        //맨 윗글자 출력
        //리셋 버튼 눌렀을 때
        listenAgainBtn.setOnClickListener(this)
        nextQuizBtn.setOnClickListener(this)
        showWordBtn.setOnClickListener(this)
    }

    private fun setViewLayout() { //뷰레이아웃설정
        wordRecyclerView.layoutManager=LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
    private fun readFile(){
        val scanner= Scanner(resources.openRawResource(R.raw.words))
        while(scanner.hasNextLine()){
            val eng=scanner.nextLine()
            val kor=scanner.nextLine()
            words.add(Word(eng,kor))
            //           array.add(word)
        }
        scanner.close()
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
//        toast(nowInning.toString()+"+"+totalInning.toString())
        if(nowInning>totalInning){
            startActivity<ShowResultActivity>("totalScore" to totalSocre, "totalInning" to totalInning)
        }

        else{
            watchedWord=false
     //       arr=words.subList(wordIndex,wordIndex+5) 원래 이거 한줄
            var idx=1
            val checkDuplicated= mutableSetOf<String>()
                arr.clear()
            //중복확인해야하는 부분 : 목록에 있으면 인덱스 넘기기 , 아니면 리스트에 추가
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
            adapter= MyAdapter(arr)
            wordRecyclerView.adapter=adapter
            wordIndex+=5
            setupAdapterListener(ansIndex)
            if(nowInning!=1 && isTTSready)
                tts.speak(quizWord.text.toString(),TextToSpeech.QUEUE_ADD,null,null)

            quizWord.visibility=View.GONE

            inningTxt.text="이닝\n$nowInning/$totalInning"
            nowInning++

        }

    }


    private fun setupAdapterListener(ansIndex:Int){
        var checked=false
        adapter.itemListener=object:MyAdapter.OnItemListener{
            override fun OnItemClick(
                holder: MyAdapter.MyViewHolder,
                view: View,
                wordTxtView: TextView,
                position: Int
            ) { //클릭 이벤트 처리
                for(i in 0..4){
                    wordRecyclerView[i].candidateWords.text=arr[i].eng+" : "+arr[i].kor
                    wordRecyclerView[i].favCheck.visibility=View.VISIBLE
                }
                nextQuizBtn.isEnabled=true

                if(checked==false ){//문제가 클릭되었는지
                    if(ansIndex==position){
                        view.setBackgroundColor(Color.GREEN)
                        if(watchedWord)
                            totalSocre++
                        else
                            totalSocre+=2
                    }
//                    tts.speak(data,TextToSpeech.QUEUE_FLUSH,null,null)
                    else{ //틀렸을 때
                        view.setBackgroundColor(Color.RED)
                        wordRecyclerView[ansIndex].setBackgroundColor(Color.GREEN)
                        favWordList.add(arr[position])
                        nowAddWordList.add(arr[position])
                        wordRecyclerView[position].favCheck.isChecked=checked
                        wordRecyclerView[position].favCheck.isEnabled=false
                    }

                    //    data.text=arr[position].eng+" : "+arr[position].kor
                    checked=true
             //       inningTxt.text="이닝\n$nowInning/$totalInning"
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
               //TODO(이거 클릭되면 점수 1점으로 바뀌는 거)
           }
       }
    }
}

//해야할 것 : 위에 듣기 로고 표시. 영어 자막 표시 숨김 / 정답 맞추면 맞았다 틀렸다 표시