@file:JvmName("CheckedExceptionToRuntime")
package com.dcen.wallet.dellet.core.util

/**
 * Promotes any exceptions thrown to [RuntimeException]
 */

interface Func<T> {
    @Throws(Exception::class)
    fun run(): T
}

interface Action {
    @Throws(Exception::class)
    fun run()
}

/**
 * Promotes any exceptions thrown to [RuntimeException]
 *
 * @param function Function to run
 * @param <T>      Return type
 * @return returns the result of the function
</T> */
fun <T> toRuntime(function: Func<T>): T {
    try {
        return function.run()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}

/**
 * Promotes any exceptions thrown to [RuntimeException]
 *
 * @param function Function to run
 */
fun toRuntime(function: Action) {
    try {
        function.run()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}