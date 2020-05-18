package com.example.wordquiz

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.android.synthetic.main.activity_add_word.addWordEngTxt
import kotlinx.android.synthetic.main.activity_add_word.addWordKorTxt
import kotlinx.android.synthetic.main.activity_add_word.confirmAddBtn
import kotlinx.android.synthetic.main.activity_add_word.showAddBtn
import kotlinx.android.synthetic.main.activity_add_word.textInputLayout
import kotlinx.android.synthetic.main.fragment_add.*
import org.jetbrains.anko.toast

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        confirmAddBtn.setOnClickListener(this)
        clearAddBtn.setOnClickListener(this)
        showAddBtn.setOnClickListener(this)
        addWordEngTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val len=  addWordEngTxt.text.toString().length
                if(len>0) confirmAddBtn.isEnabled=true
                else confirmAddBtn.isEnabled=false
                var hasProblem=false
                for(w in WordBook.wordList){
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
                if(LaunchActivity.hasKorean(eng) || !LaunchActivity.hasKorean(kor) || kor==""){
                    Log.d("dbdb","이건 오류: "+eng+" "+kor)
                    Toast.makeText(activity, "단어가 정상적으로 저장되지 않았습니다.",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity,"단어가 정상적으로 저장되었습니다.",Toast.LENGTH_SHORT).show()
                    LaunchActivity.addit(Word(eng,kor),mode=2) //추가목록 추가
                }
                addWordEngTxt.setText("")
                addWordKorTxt.setText("")
                //     val i=Intent(this,ListActivity::class.java)
                //     startActivity(i)
                Log.d("dbdb", "추가:"+WordBook.addedWordList.toString())
            }
            R.id.clearAddBtn-> {
                addWordEngTxt.setText("")
                addWordKorTxt.setText("")
            }
            R.id.showAddBtn->{
                val i= Intent(activity,ShowAddWordsActivity::class.java)
                startActivity(i)
            }

        }
    }

}
