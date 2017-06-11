package io.github.gedorinku.supercon2017_test

/**
 * Created by gedorinku on 2017/06/11.
 */
interface OutputVerifier {

    fun verify(input: String, output: String): Result

    data class Result(val isValid: Boolean, val message: String)
}