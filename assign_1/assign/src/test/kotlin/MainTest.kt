import com.ourstory.service.fibonacci.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder


@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class MainTest {

    /* 해당 변수의 값을 변경하면 값만큼 피보나치 수열 계산 */
    val n = 50

    @AfterEach
    fun afterEach() {
        println()
    }

    @DisplayName("1. 재귀 호출로 피보나치 수열 계산")
    @Test
    @Order(value = 1)
    fun recursive() {
        val fibonacci = FibonacciRecursive()
        println("Fibonacci Recursive: ")
        for (i in 1..n) {
            val result = fibonacci.fibonacci(i)
            println("F($i) = $result")
        }
    }

    @DisplayName("2. 동적 프로그래밍 - 배열 메모이제이션")
    @Test
    @Order(2)
    fun dpArray() {
        val fibonacci = FibonacciDPArray()

        println("Fibonacci DP Array: ")
        for (i in 1..n) {
            val result = fibonacci.fibonacci(i)
            println("F($i) = $result")
        }
    }

    @DisplayName("3. 동적 프로그래밍 - 맵 메모이제이션")
    @Test
    @Order(3)
    fun dpMap() {
        val fibonacci = FibonacciDPMap()

        println("Fibonacci DP Map: ")
        for (i in 1..n) {
            val result = fibonacci.fibonacci(i)
            println("F($i) = $result")
        }
    }

    @DisplayName("4. 반복적 구현으로 피보나치 수열 계산")
    @Test
    @Order(4)
    fun iterative() {
        val fibonacci = FibonacciIterative()

        println("Fibonacci Iterative: ")
        for (i in 1..n) {
            val result = fibonacci.fibonacci(i)
            println("F($i) = $result")
        }
    }

    @DisplayName("5. 행렬 거듭제곱으로 피보나치 수열 계산")
    @Test
    @Order(5)
    fun matrix() {
        val fibonacci = FibonacciMatrix()

        println("Fibonacci Matrix: ")
        for (i in 1..n) {
            val result = fibonacci.fibonacci(i)
            println("F($i) = $result")
        }
    }

}