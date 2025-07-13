package com.ourstory.service.fibonacci

import java.math.BigInteger

class FibonacciDPMap : FibonacciService {

    private val memo = mutableMapOf<Int, BigInteger>()

    override fun fibonacci(n: Int): BigInteger {
        // 기본 케이스
        if (n <= 1) return BigInteger.valueOf(n.toLong())

        // 이미 계산된 값이 있으면 반환 (메모이제이션)
        memo[n]?.let { return it }

        // 재귀 호출하여 계산하고 결과를 메모에 저장
        val result = fibonacci(n - 1) + fibonacci(n - 2)
        memo[n] = result

        return result
    }

}