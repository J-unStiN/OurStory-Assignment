package com.ourstory.service.fibonacci

import java.math.BigInteger

class FibonacciRecursive : FibonacciService {

    override fun fibonacci(n: Int): BigInteger {
        // 기본 케이스: F(0) = 0, F(1) = 1
        if (n <= 1) return BigInteger.valueOf(n.toLong())

        // 재귀 호출: F(n) = F(n-1) + F(n-2)
        return fibonacci(n - 1) + fibonacci(n - 2)
    }

}