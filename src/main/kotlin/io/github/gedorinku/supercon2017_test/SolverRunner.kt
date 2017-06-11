package io.github.gedorinku.supercon2017_test

import java.util.concurrent.TimeUnit

/**
 * Created by gedorinku on 2017/06/09.
 */
class SolverRunner(val command: String) {

    fun run(inputCase: String): SolverResult {
        val start = System.nanoTime()
        val output = StringBuilder()
        val process = Runtime.getRuntime().exec(command)

        process.outputStream.bufferedWriter().use {
            it.write(inputCase)
            it.flush()
        }

        process.waitFor(10, TimeUnit.SECONDS)

        val duration = System.nanoTime() - start

        process.inputStream.bufferedReader().use {
            it.lines().forEach {
                output.appendln(it)
            }
        }

        return SolverResult(process.exitValue(), output.toString(), duration)
    }

    data class SolverResult(val exitStatus: Int, val output: String, val nanoTime: Long)
}