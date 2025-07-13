package com.ourstory.service.fibonacci

import java.math.BigInteger

interface FibonacciService {

    fun fibonacci(n: Int): BigInteger
}