package com.example.wordquiz

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        //원래는 5 테스팅 과정
        return 5
    }

    override fun createFragment(position: Int): Fragment {
   //     Log.d("dbdb","MyFragmentStateAdapter pos:"+position)
        val fragment=when(position){
            0->QuizFragment()
            1->ListFragment()
            2->AddFragment()
            3->FavFragment()
            4-> {
                SettingFragment()
            }
            else->QuizFragment()
            }
        return fragment
        //0-4에서 1-5로 바꿔봄
        }
    }
    //각 페이지에 Fragment를 제공하는 Adapter
