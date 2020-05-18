package com.example.wordquiz

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
data class word(var eng:String, var num:String){}

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {


        val str="sa"
        val a=str.split("/ ")
        println(a)
        val cards = mutableListOf<word>()
        cards.add(word("a", "1"))
//        val w = word("a", "2")

        fun addit(w: word) {
            for (i in 0..cards.size-1) {
                if(w.eng == cards[i].eng){
                    cards[i].num += "/${w.num}"
                    return
                }
            }
            cards.add(w)
        }
        addit(word("b", "2"))
        addit(word("a", "2"))

        println(cards)
    }

}
