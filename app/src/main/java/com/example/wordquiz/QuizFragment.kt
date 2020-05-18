package com.example.wordquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    var quizNum=0 //퀴즈 수
    var quizType=true //문제 유형 - true:2점제 , false:1점제

    interface  callSetingListener{
        fun changeQuizInfo(num:Int, type:Boolean)
    }
    companion object{
        //setting fragment에서 인자를 전달받아야하는데 fragment는 받을 수 없으므로 static 함수 통해 정의
        fun newQuizFragment(num:Int, type:Boolean):QuizFragment{
            val quizFragment=QuizFragment()
            quizFragment.quizNum=num
            quizFragment.quizType=type

            return quizFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun setQuizInfo(num:Int,type:Boolean){
        quizNum=num
        quizType=type
    }
    private fun init() {
        gotoSettingTxt.setOnClickListener{
            val intent=Intent(activity,LaunchActivity::class.java)
            intent.putExtra("GO","SETTING")
            startActivity(intent)
        }
        quizNumTxt.text="퀴즈 수 : "+quizNum.toString()+"문제"
        var type="음성 모드 허용"
        if(!quizType)
            type="음성 모드 불가"
        quizTypeTxt.text="퀴즈 유형 : "+type
        quizStartBtn.setOnClickListener {
            val intent=Intent(activity,MainActivity::class.java)
          //  startActivity(intent) TODO-이거 누르면 몇문젠지 무슨 타입인지 toast 출력해보자
            if(activity is QuizFragment.callSetingListener){
                val quizFrag=activity as QuizFragment.callSetingListener
                quizFrag.changeQuizInfo(quizNum,quizType)
            }
        }
        setQuizInfo(quizNum,quizType)
    quizStartBtn.setOnClickListener {
        val intent=Intent(activity,QuizActivity::class.java)
        intent.putExtra("num",quizNum)
        intent.putExtra("type",quizType)
        startActivity(intent)
    }
    }


}
