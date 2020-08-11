package com.chen.app

fun main(args: Array<String>) {

    fun multiply(num1: String, num2: String): String {
        if (num1 == "0" || num2 == "0") return "0"
        val l = num1.length + num2.length
        val result = IntArray(l) { 0 }
        val num0 = '0'.toInt()

        for (idx1 in num1.length - 1 downTo 0) {
            for (idx2 in num2.length - 1 downTo 0) {
                val curr = idx1 + idx2 + 1
                val next = curr - 1
                val value = result[curr] + ((num1[idx1].toInt() - num0) * (num2[idx2].toInt() - num0))
                result[curr] = value % 10
                result[next] = result[next] + value / 10

            }
        }

        return result.filterIndexed { index, i ->  index != 0 || i != 0}.joinToString("")
    }


    println(System.currentTimeMillis())
    val a = multiply("9", "99")
    println(System.currentTimeMillis())
    println(a)
//    fun lengthOfLongestSubstring(s: String): Int {
//        val lastIndex = IntArray(128) { -1 }
//        var start = 0
//        var res = 0
//        for (index in s.indices) {
//            start = max(start, lastIndex[s[index].toInt()] + 1)
//            res = max(res, index - start + 1)
//            lastIndex[s[index].toInt()] = index
//        }
//        return res
//    }
//
//    println(System.currentTimeMillis())
//    val a = lengthOfLongestSubstring(
//        "aqwertyuiopaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedlkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedaqwertyuioplkjhgfdxcvbnmmnbvcxzasdfrtyuiuytrdsxcvhjuytfdxcvbjuytfdxdrtyuiqwertyuioplkjhgfdsazxcvbnmnbvcxzxcvbnmmnbvcxzasdfghjklpoiuytrewbcabcdwaeedhuwv"
//    )
//    println(System.currentTimeMillis())
//
//    println(a)


//    println(setResult.toString())
}