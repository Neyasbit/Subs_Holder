package com.core.common

interface Matches<T> {
    fun matches(data: T): Boolean
}
