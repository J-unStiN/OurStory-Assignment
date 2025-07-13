package com.ourstory.service.fibonacci

import java.math.BigInteger

class FibonacciIterative : FibonacciService {

    override fun fibonacci(n: Int): BigInteger {
        // 기본 케이스
        if (n <= 1) return BigInteger.valueOf(n.toLong())

        var prev = BigInteger.ZERO
        var curr = BigInteger.ONE

        // Bottom-Up 방식으로 반복 계산
        repeat(n - 1) {
            val next = prev + curr
            prev = curr
            curr = next
        }

        return curr
    }
}
