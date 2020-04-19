package com.example.wordquiz

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
data class word(val eng:String, val num:Int){}

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val cards= mutableListOf<word>()
        cards.add(word("a",1))
        cards.add(word("b",2))
        cards.add(word("c",3))
        cards.add(word("d",4))
        cards.add(word("e",5))

        Collections.shuffle(cards)
        println(cards)
        val col=cards.subList(1,3);
        println(col);

        for(i in 1..100)
            println((0..5).random())
    }

}
