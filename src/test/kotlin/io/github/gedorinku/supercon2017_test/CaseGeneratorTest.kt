package io.github.gedorinku.supercon2017_test

import org.junit.Test
import java.util.*

/**
 * Created by gedorinku on 2017/06/09.
 */
class CaseGeneratorTest {
    @Test
    fun generate() {
        val random = Random()
        val generator = CaseGenerator(random)
        val range = 1..2000
        val numOfCases = 50000

        (1..numOfCases).forEach {
            if (it % 1000 == 0) {
                println("tested $it cases / $numOfCases cases")
            }
            val probability = random.nextInt(20) + 1
            val case = generator.generate(range, probability)
            val (isValid, message) = InputCaseValidChecker.checkValid(case)
            assert(isValid) {
                "$message\n" +
                        "==begin of case==\n" +
                        case +
                        "==end of case=="}
        }
    }

}