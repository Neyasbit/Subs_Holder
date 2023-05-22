package com.core.common

interface Converter<F, T> {

    fun convert(from: F): T

    fun revert(from: T): F {
        throw UnsupportedOperationException("Revert not supported")
    }

    fun convertList(from: List<F>): List<T> = from.map(::convert)

    fun revertList(from: List<T>): List<F> = from.map(::revert)
}
