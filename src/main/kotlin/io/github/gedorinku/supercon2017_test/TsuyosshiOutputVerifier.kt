package io.github.gedorinku.supercon2017_test

/**
 * Created by gedorinku on 2017/06/11.
 */
class TsuyosshiOutputVerifier : OutputVerifier {

    override fun verify(input: String, output: String): OutputVerifier.Result {
        val process = Runtime.getRuntime().exec("./verifier.out")
        process.outputStream.bufferedWriter().use {
            it.write(input)
            it.write(output)
            it.flush()
        }

        process.waitFor()

        val result = StringBuilder()

        process.inputStream.bufferedReader().use {
            while (true) {
                val line = it.readLine() ?: break
                result.appendln(line)
            }
        }

        if (result.isEmpty()) {
            return OutputVerifier.Result(true, "")
        }

        return OutputVerifier.Result(false, result.toString())
    }
}