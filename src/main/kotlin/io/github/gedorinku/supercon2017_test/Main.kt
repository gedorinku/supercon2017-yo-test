package io.github.gedorinku.supercon2017_test

import java.io.File
import java.io.PrintStream
import java.time.LocalDateTime

/**
 * Created by gedorinku on 2017/06/09.
 */
fun main(args: Array<String>) {
    println(LocalDateTime.now())

    if (args.isNotEmpty()) {
        if (args[0] == "--verify") {
            verify()
        } else {
            val nRange = args[0].toInt()..args[1].toInt()
            val probability = args[2].toInt()
            runTest(nRange, probability)
        }
    } else {
        runTest()
    }
}

fun runTest(nRange: IntRange = 1..20, probability: Int = 5) {
    val generator = CaseGenerator()
    val solverRunner = SolverRunner("./a.out")
    val outputVerifier = TsuyosshiOutputVerifier()
    var totalCount = 0
    var totalYesCount = 0
    var totalM = 0L
    var totalMilliSeconds = 0L
    var totalMilliSecondsYes = 0L
    var maxTime = 0.0
    var maxM = 0

    try {
        while (true) {
            val input = generator.generate(nRange, probability)
            val inputVerifyResult = InputCaseVerifier.verify(input)
            if (!inputVerifyResult.isValid) {
                File("bad-input-${LocalDateTime.now()}.log").writer().use {
                    it.write("$inputVerifyResult\n")
                    it.write("$input\n")
                }
                return
            }

            val runResult = solverRunner.run(input)
            if (runResult.exitStatus != 0 || 10 * 1000 < runResult.nanoTime / (1000 * 1000)) {
                writeErrorLog(runResult, null, input, "bad-exit-${LocalDateTime.now()}.log")
                return
            }

            val outputVerifyResult = outputVerifier.verify(input, runResult.output)
            if (!outputVerifyResult.isValid) {
                writeErrorLog(
                        runResult,
                        outputVerifyResult,
                        input,
                        "bad-output-${LocalDateTime.now()}.log"
                )
                return
            }

            totalCount++
            val time = runResult.nanoTime / (1000.0 * 1000.0)
            maxTime = maxOf(maxTime, time)
            totalMilliSeconds += time.toLong()
            val output = runResult.output.split('\n').map { it.trim() }
            if (output[0] == "YES") {
                totalYesCount++
                val m = output[1].trim().toInt()
                maxM = maxOf(maxM, m)
                totalM += m
                totalMilliSecondsYes += runResult.nanoTime / (1000 * 1000)
            }

            if (totalCount % 250 == 0) {
                val mAverage = if (totalYesCount == 0) {
                    "NaN"
                } else {
                    (totalM / totalYesCount.toDouble()).toString()
                }
                val yesMillisAverage = if (totalYesCount == 0) {
                    "NaN"
                } else {
                    (totalMilliSecondsYes / totalYesCount).toString()
                }
                println("$totalCount cases passed.\n" +
                        "time(ave.):${time / totalCount} ms\n" +
                        "time(max):$maxTime ms\n" +
                        "time(yes only ave.):$yesMillisAverage ms\n" +
                        "m(ave.):$mAverage  m(max):$maxM\n")
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        File("crash-${LocalDateTime.now()}.log").printWriter().use {
            e.printStackTrace(it)
        }
    }
}

fun verify() {
    TODO("標準入力から解を読んで検証")
}

fun writeErrorLog(runResult: SolverRunner.SolverResult,
                  outputVerifyResult: OutputVerifier.Result?,
                  input: String,
                  filename: String) {
    File(filename).writer().use {
        it.write("exit status:${runResult.exitStatus}\n" +
                "time:${runResult.nanoTime / (1000.0 * 1000.0)}ms\n" +
                "$outputVerifyResult\n" +
                "==begin input==\n" +
                input +
                "==end input==\n" +
                "==begin output==\n" +
                runResult.output +
                "==end output==\n")
    }
}
