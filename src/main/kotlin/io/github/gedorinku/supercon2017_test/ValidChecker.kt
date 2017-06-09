package io.github.gedorinku.supercon2017_test

/**
 * Created by gedorinku on 2017/06/09.
 */
object ValidChecker {

    fun checkValid(case: String): Result{
        throw NotImplementedError()
    }

    data class Result(val isValid: Boolean, val message: String)
}