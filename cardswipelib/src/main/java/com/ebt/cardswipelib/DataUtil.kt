package com.ebt.cardswipelib

object DataUtil {
    internal fun <T> checkIsNull(t: T?): T {
        t?.let {
            return t
        }
        throw NullPointerException()
    }
}