package com.example.wordquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.toast

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment(),View.OnClickListener {
    //문제 유형, 문제 수를 설정하는 프래그먼트
    var quizNum=12
    var quizType=true

    interface callSettingListener{
        fun setSettingFrag(num:Int, type:Boolean)
    }

    companion object{
        //setting fragment에서 인자를 전달받아야하는데 fragment는 받을 수 없으므로 static 함수 통해 정의
        fun newSettingFragment(num:Int, type:Boolean):SettingFragment{
            Log.d("dbdb","NEW SETTING!!!")
            val settingFragment=SettingFragment()
            settingFragment.quizNum=num
            settingFragment.quizType=type
            Log.d("dbdb","NEW) Num:"+num+" Type:"+type)

            return settingFragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("dbdb","CREATE VIEW ")
        if(arguments!=null){
            Log.d("dbdb","인자 있어요!!!!!!!!!")
            quizNum=arguments!!.getInt("quizNum")
            quizType=arguments!!.getBoolean("quizType")
            Log.d("dbdb","제발"+quizNum+quizType)

        }
        else
            Log.d("dbdb","인자 없음")

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("dbdb","SETTING NEW:"+quizNum+" Type:"+quizType)

        quizCntTxt.text=quizNum.toString()
        quizTypeSwitch.isChecked=quizType

        quizCntDownBtn.setOnClickListener(this)
        quizCntUpBtn.setOnClickListener(this)
        quizModeHelpBtn.setOnClickListener(this)
        quizTypeSwitch.setOnClickListener(this)
        syncWithActivity()
    }

    fun setSettings(num:Int, type:Boolean){
        quizType=type
        quizNum=num
    }
    fun syncWithActivity(){
        Log.d("dbdb","SETTING CNT: "+quizNum+" TYPE:"+quizType)
        if(activity is SettingFragment.callSettingListener){
            val settingFrag=activity as SettingFragment.callSettingListener
            settingFrag.setSettingFrag(quizNum,quizType)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.quizCntDownBtn -> {
                val cnt = quizCntTxt.text.toString().toInt()
                if ((cnt > 1) && (cnt < 21)){
                    quizCntTxt.text = (cnt - 1).toString()
                    quizNum--
                }
                else
                    Toast.makeText(activity, "값 범위 (1~20)", Toast.LENGTH_SHORT).show()
            }
            R.id.quizCntUpBtn -> {
                val cnt = quizCntTxt.text.toString().toInt()
                if ((cnt > 0) && (cnt < 20)){
                    quizCntTxt.text = (cnt + 1).toString()
                    quizNum++
                }
                else
                    Toast.makeText(activity, "값 범위 (1~20)", Toast.LENGTH_SHORT).show()
            }
            R.id.quizModeHelpBtn -> {
                Toast.makeText(
                    activity, """
                    음성 모드 [ON] 
                    단어가 보이지 않고 무제한 듣기 가능
                     -> 안보고 맞추면 +2점, 보고 맞추면 +1점
                    
                    음성 모드 [OFF] 
                    단어가 보이고 무제한 듣기 가능
                    -> 맞추면 +1점
                """.trimIndent(), Toast.LENGTH_LONG
                ).show()

            }
            R.id.quizTypeSwitch->{
                if(quizTypeSwitch.isChecked){
                    quizType=true
                }
                else{
                    quizType=false
                }
            }
        }
        syncWithActivity()
    }


    override fun onStop() {
        super.onStop()
        Log.d("dbdb","STOP")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("dbdb","DESTROY VIEW")
    }
    override fun onDetach() {
        super.onDetach()
        Log.d("dbdb","DETACHED")
    }
    override fun onStart() {
        super.onStart()
        Log.d("dbdb","START")
    }
    override fun onResume() {
        super.onResume()
        Log.d("dbdb","RESUME")

    }

}
