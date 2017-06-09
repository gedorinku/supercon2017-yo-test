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

        (1..100).forEach {
            val probability = random.nextInt(20) + 1
            val (isValid, message) = ValidChecker.checkValid(
                    generator.generate(range, probability)
            )
            assert(isValid) { message }
        }
    }

}