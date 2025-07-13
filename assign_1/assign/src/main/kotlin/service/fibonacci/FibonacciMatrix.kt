package com.ourstory.service.fibonacci

import java.math.BigInteger

class FibonacciMatrix : FibonacciService {

    override fun fibonacci(n: Int): BigInteger {
        // 기본 케이스
        if (n <= 1) return BigInteger.valueOf(n.toLong())

        // 피보나치 행렬: [[1, 1], [1, 0]]
        val baseMatrix = arrayOf(
            arrayOf(BigInteger.ONE, BigInteger.ONE),
            arrayOf(BigInteger.ONE, BigInteger.ZERO)
        )

        val result = matrixPower(baseMatrix, n - 1)
        return result[0][0]
    }

    private fun matrixPower(matrix: Array<Array<BigInteger>>, n: Int): Array<Array<BigInteger>> {
        if (n == 1) return matrix

        val half = matrixPower(matrix, n / 2)
        val result = matrixMultiply(half, half)

        return if (n % 2 == 0) result else matrixMultiply(result, matrix)
    }

    private fun matrixMultiply(
        a: Array<Array<BigInteger>>,
        b: Array<Array<BigInteger>>
    ): Array<Array<BigInteger>> {
        return arrayOf(
            arrayOf(
                a[0][0] * b[0][0] + a[0][1] * b[1][0],
                a[0][0] * b[0][1] + a[0][1] * b[1][1]
            ),
            arrayOf(
                a[1][0] * b[0][0] + a[1][1] * b[1][0],
                a[1][0] * b[0][1] + a[1][1] * b[1][1]
            )
        )
    }
}
