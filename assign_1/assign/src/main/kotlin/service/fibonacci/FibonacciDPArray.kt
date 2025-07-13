package com.ourstory.service.fibonacci

import java.math.BigInteger

class FibonacciDPArray : FibonacciService {

    private val memo: Array<BigInteger?> = Array(1000) { null }

    override fun fibonacci(n: Int): BigInteger {
        // 기본 케이스
        if (n <= 1) return BigInteger.valueOf(n.toLong())

        // 배열 크기를 초과하는 경우 Map 방식으로 fallback
        if (n >= memo.size) {
            return fibonacci(n - 1) + fibonacci(n - 2)
        }

        // 이미 계산된 값이 있으면 반환
        memo[n]?.let { return it }

        // 재귀 호출하여 계산하고 결과를 배열에 저장
        val result = fibonacci(n - 1) + fibonacci(n - 2)
        memo[n] = result

        return result
    }

}