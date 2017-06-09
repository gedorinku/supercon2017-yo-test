package io.github.gedorinku.supercon2017_test

/**
 * Created by gedorinku on 2017/06/09.
 */
object InputCaseValidChecker {

    fun checkValid(case: String): Result {
        val lines = case.split('\n').map { it.trim() }
        if (lines.isEmpty()) {
            return Result(false, "空文字列です。")
        }

        val n = lines[0].toIntOrNull()
                ?: return Result(false, "nがパースできない。n=\"${lines[0]}\"")
        if (n !in 1..2000) {
            return Result(false, "nが制約の範囲外。n=$n")
        }

        val seeds = lines.subList(1, lines.lastIndex)
        if (seeds.size != n) {
            return Result(false, "原種の数がnと一致しない。n=$n 原種数=${seeds.size}")
        }

        seeds.forEachIndexed { index, s ->
            val result = checkValidSeed(s, index, n)
            if (!result.isValid) {
                return result
            }
        }

        return Result(true, "")
    }

    private fun checkValidSeed(seed: String, index: Int, n: Int): Result {
        if (seed.isEmpty()) {
            return Result(false, "原種が空文字列。index=$index n=$n")
        }

        if (52 < seed.length) {
            return Result(
                    false,
                    "原種文字列の長さがデカすぎる。length=${seed.length} seed=\"$seed\" index=$index"
            )
        }

        var preLower: Char = 0.toChar()
        var pre: Char = 0.toChar()
        seed.forEach {
            if (it.toLowerCase() < preLower) {
                return Result(false, "文字がソートされていない。seed=\"$seed\" index=$index")
            }

            if (it.isLowerCase()) {
                if (preLower == it) {
                    return Result(
                            false,
                            "同じアルファベットの小文字の前に大文字があるか、同じ文字が連続している。seed=\"$seed\" index=$index"
                    )
                }
            } else if (pre == it) {
                return Result(
                        false,
                        "同じ文字が連続している。seed=\"$seed\" index=$index"
                )
            }

            pre = it
            preLower = it.toLowerCase()
        }

        return Result(true, "")
    }

    data class Result(val isValid: Boolean, val message: String)
}