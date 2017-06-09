package io.github.gedorinku.supercon2017_test

import java.util.*

/**
 * Created by gedorinku on 2017/06/09.
 */
class CaseGenerator(private val random: Random = Random()) {

    /***
     * ケースを生成します。
     * @param nRange nはこの区間からランダムに決定されます。
     * @param probability 各原種について、ある文字cが含まれる確率は 1/(probability) になります。ただし、各原種が空文字列になることはありません。
     */
    fun generate(nRange: IntRange, probability: Int): String {
        val n = nRange.first + random.nextInt(nRange.count())
        val result = StringBuilder()

        (1..n).forEach {
            var flag = true
            while (flag) {
                ('a'..'z').forEach {
                    c ->
                    arrayOf(true, false).forEach {
                        if (random.nextInt(probability) == 0) {
                            result.append(if (it) {
                                c
                            } else {
                                c.toUpperCase()
                            })
                            flag = false
                        }
                    }
                }
            }

            result.appendln()
        }

        return result.toString()
    }
}