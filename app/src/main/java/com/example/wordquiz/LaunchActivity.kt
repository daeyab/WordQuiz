package com.example.wordquiz

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.wordquiz.WordBook.addedWordList
import com.example.wordquiz.WordBook.wordList
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class LaunchActivity  : AppCompatActivity(),SettingFragment.callSettingListener {
//메인 엑티비티

    var quizNum = 10
    var quizType = true

    val menuarray = arrayListOf<Int>(
        R.drawable.quiz_icon,
        R.drawable.list_icon,
        R.drawable.add_icon,
        R.drawable.favorite_icon,
        R.drawable.settings_icon
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        init()
    }

    fun setRadioBtn() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.QuizRBtn -> {
                    setRadioBtnColor(1)
                    makeQuizFrag()
                }
                R.id.ListRBtn -> {
                    setRadioBtnColor(2)
                    makeListFrag()
                }
                R.id.AddRBtn -> {
                    setRadioBtnColor(3)
                    makeAddFrag()
                }
                R.id.FavRBtn -> {
                    setRadioBtnColor(4)
                    makeFavFrag()
                }
                R.id.SettingRBtn -> {
                    setRadioBtnColor(5)
                    makeSettingFrag()
                }
            }
        }
    }
    

    fun setRadioBtnColor(num:Int){
        SettingRBtn.setBackgroundResource(R.color.tabgray)
        QuizRBtn.setBackgroundResource(R.color.tabgray)
        ListRBtn.setBackgroundResource(R.color.tabgray)
        AddRBtn.setBackgroundResource(R.color.tabgray)
        FavRBtn.setBackgroundResource(R.color.tabgray)
        when(num){
           1->QuizRBtn.setBackgroundResource(R.color.mygray)
           2->ListRBtn.setBackgroundResource(R.color.mygray)
           3->AddRBtn.setBackgroundResource(R.color.mygray)
           4->FavRBtn.setBackgroundResource(R.color.mygray)
           5->SettingRBtn.setBackgroundResource(R.color.mygray)

        }
    }
    fun makeSettingFrag(quizNum: Int = this.quizNum, quizType: Boolean = this.quizType) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        if (fragment == null) {
            val settingTransaction = supportFragmentManager.beginTransaction()
            val settingFragment = SettingFragment.newSettingFragment(quizNum, quizType)
            settingTransaction.replace(R.id.frame, settingFragment, "settingFrag")
            settingTransaction.commit()
        } else {//이미지인지 텍스트인지 구분
            val settingFragment = supportFragmentManager.findFragmentByTag("settingFrag")
            if (settingFragment == null) {
                val settingTransaction = supportFragmentManager.beginTransaction()
                val settingFragment = SettingFragment.newSettingFragment(quizNum, quizType)
                settingTransaction.replace(R.id.frame, settingFragment, "settingFrag")
                settingTransaction.commit()
            } else { //부착이 되어있는 경우
                (settingFragment as SettingFragment).setSettings(quizNum, quizType)
                this.quizNum = quizNum
                this.quizType = quizType
            }
        }
    }
    fun makeQuizFrag(){
       val fragment = supportFragmentManager.findFragmentById(R.id.frame)
       if (fragment == null) {
           val quizTransaction = supportFragmentManager.beginTransaction()
           val quizFragment = QuizFragment.newQuizFragment(quizNum, quizType)
           quizTransaction.replace(R.id.frame, quizFragment, "quizFrag")
           quizTransaction.commit()
       } else {//이미지인지 텍스트인지 구분
           val quizFragment = supportFragmentManager.findFragmentByTag("quizFrag")
           if (quizFragment == null) {
               val quizTransaction = supportFragmentManager.beginTransaction()
               val quizFragment = QuizFragment.newQuizFragment(quizNum, quizType)
               quizTransaction.replace(R.id.frame, quizFragment, "quizFrag")
               quizTransaction.commit()
           } else { //부착이 되어있는 경우
               //  (quizFragment as QuizFragment).setImageNum(num)
           }
       }
   }
    fun makeListFrag(){
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        if (fragment == null) {
            val listTransaction = supportFragmentManager.beginTransaction()
            val listFragment = ListFragment()
            listTransaction.replace(R.id.frame, listFragment, "listFrag")
            listTransaction.commit()
        } else {//이미지인지 텍스트인지 구분
            val listFragment = supportFragmentManager.findFragmentByTag("listFrag")
            if (listFragment == null) {
                val listTransaction = supportFragmentManager.beginTransaction()
                val listFragment = ListFragment()
                listTransaction.replace(R.id.frame, listFragment, "listFrag")
                listTransaction.commit()
            } else { //부착이 되어있는 경우
                //  (quizFragment as QuizFragment).setImageNum(num)
            }
        }
    }
    fun makeAddFrag(){     
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        if (fragment == null) {
            val addTransaction = supportFragmentManager.beginTransaction()
            val addFragment = AddFragment()
            addTransaction.replace(R.id.frame, addFragment, "addFrag")
            addTransaction.commit()
        } else {//이미지인지 텍스트인지 구분
            val addFragment = supportFragmentManager.findFragmentByTag("addFrag")
            if (addFragment == null) {
                val addTransaction = supportFragmentManager.beginTransaction()
                val addFragment = AddFragment()
                addTransaction.replace(R.id.frame, addFragment, "addFrag")
                addTransaction.commit()
            } else { //부착이 되어있는 경우
                //  (quizFragment as QuizFragment).setImageNum(num)
            }
        }
    }
    fun makeFavFrag(){
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        if (fragment == null) {
            val favTransaction = supportFragmentManager.beginTransaction()
            val favFragment = FavFragment()
            favTransaction.replace(R.id.frame, favFragment, "favFrag")
            favTransaction.commit()
        } else {//이미지인지 텍스트인지 구분
            val favFragment = supportFragmentManager.findFragmentByTag("favFrag")
            if (favFragment == null) {
                val favTransaction = supportFragmentManager.beginTransaction()
                val favFragment = FavFragment()
                favTransaction.replace(R.id.frame, favFragment, "favFrag")
                favTransaction.commit()
            } else { //부착이 되어있는 경우
                //  (quizFragment as QuizFragment).setImageNum(num)
            }
        }
    }


    override fun setSettingFrag(num: Int, type: Boolean) {
        makeSettingFrag(num, type)
    }

    private fun init() {
        val i=intent.getStringExtra("GO")
        when(i){
            "SETTING"->{
                setRadioBtnColor(5)
                makeSettingFrag()
            }
            "ADD"->{
                setRadioBtnColor(3)
                makeAddFrag()
            }
            else->{
                setRadioBtnColor(1)
                makeQuizFrag()
            }
        }

        readFile()
        setRadioBtn()
        topImg.setOnClickListener {
            Log.d("dbdb", "Num:" + quizNum + " Type:" + quizType)
        }
    }

    companion object {
        fun addit(w: Word, mode: Int = 1) {

            var isin1 = false
            var isin2 = false

            if (mode == 2) {
                for (i in 0..addedWordList.size!! - 1) {
                    if (w.eng == addedWordList[i].eng) {
                        //        Log.d("dbdb",w.eng+" 단어는 존재해 ")
                        val oldkorval = addedWordList[i].kor.split("/ ")
                        val newkorval = w.kor.split("/ ")
                        for (str in newkorval) {
                            if (!oldkorval.contains(str))
                                addedWordList[i].kor += "/ $str"
                        }
                        isin2 = true
                        break; //추가 list에 추가가 완료됨
                    }
                }
            }
            for (i in 0..wordList.size!! - 1) {
                if (w.eng == wordList[i].eng) {
                    //   Log.d("dbdb",w.eng+" 단어는 존재해 ")
                    val oldkorval = wordList[i].kor.split("/ ")
                    val newkorval = w.kor.split("/ ")
                    for (str in newkorval) {
                        if (!oldkorval.contains(str))
                            wordList[i].kor += "/ $str"
                    }
                    isin1 = true
                    break //전체 list에 추가가 완료됨
                }
            }
            when (mode) {
                1 -> {
                    if (isin1 == false)
                    //      Log.d("dbdb","전체")
                        WordBook.wordList.add(w)
                }
                2 -> {
                    if (isin2 == false) {
                        WordBook.addedWordList.add(w)
                    }
                    if (isin1 == false) {
                        WordBook.wordList.add(w)
                    }
                    //      Log.d("dbdb","추가")
                }
            }
        }

        fun hasKorean(s: String): Boolean {
            //한글이 있는지 체크
            for (i in 0..s.length - 1) {
                if (s[i].toInt() >= 128 && s[i] != '～') {
                    //     Log.d("dbdb",s[i].toString())
                    return true
                }
            }
            return false
        }
    }

    private fun readFile() {
        //raw data file에서 단어를 형식에 맞게 읽어오는 함수
        val scanner = Scanner(resources.openRawResource(R.raw.words))
        while (scanner.hasNextLine()) {
            var eng = scanner.nextLine()
            var kor = scanner.nextLine()
            if (hasKorean(eng) || !hasKorean(kor)) {
                Log.d("dbdb", "이건 오류: " + eng + " " + kor)
                continue
            }
            addit(Word(eng, kor))
        }
        scanner.close()
        WordBook.wordList.sortBy { word -> word.eng }
    }






    fun getSettingFrag() {
        val settingFragment = SettingFragment()
        var args = Bundle()
        args.putInt("quizNum", quizNum)
        args.putBoolean("quizType", quizType)
        settingFragment.arguments = args

        val settingTransaction = supportFragmentManager.beginTransaction()
        settingTransaction.add(settingFragment, "settingFrag")
        settingTransaction.commit()
    }


}

    /*  override fun setSettingFrag(num: Int, type: Boolean) {
          val fragment=supportFragmentManager.findFragmentByTag("settingFrag")
  //        if(fragment==null){
  //            Log.d("dbdb","setting fragment is null!")
          val settingFragment=SettingFragment()
          var args=Bundle()
          args.putInt("quizNum",quizNum)
          args.putBoolean("quizType",quizType)
          settingFragment.arguments=args
          val settingTransaction=supportFragmentManager.beginTransaction()
          settingTransaction.commit()

          //    settingTransaction.replace(R.id.viewPager,settingFragment,"settingFrag")
              settingTransaction.add(settingFragment,"settingFrag")
    //      }
     //    else{
              //TODO(이거 자꾸 존재하는데 여기서 settings를 10,true로 설정시켜주는 것 같다.
              //만약에 else 없이 잘 돌아간다면 이대로갖ㄴ다
         //     Log.d("dbdb","fragment 존재해요")
              (fragment as SettingFragment).setSettings(quizNum,quizType)
          }
          quizNum=num
          Log.d("dbdb","!!!!!!!!!!!! Num:"+quizNum+" Type:"+quizType)
          quizType=type
          Log.d("dbdb","!?!?!?!?")
          settingTransaction.remove(settingFragment)

      }*/

    //TODO - 문제 유형 / 문제 종류  -> 화면에
    //TODO - SettingFragment에서 문제 수랑 유형 받아오면 될 듯


