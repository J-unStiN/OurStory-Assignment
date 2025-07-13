package com.ourstory.assign_fcfs.controller

import com.ourstory.assign.dto.req.JoinEventRequest
import com.ourstory.assign_fcfs.repository.EventFCFSRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.stream.IntStream

class EventFCFSControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var repo: EventFCFSRepository

    @RepeatedTest(100) // 100 thread × 100 iteration = 10,000 요청
    fun concurrentJoin() {
        val thread = 100
        val es = Executors.newFixedThreadPool(thread)
        val latch = CountDownLatch(10_000)

        IntStream.range(0, 10_000).forEach { i ->
            es.submit {
                try {
                    restTemplate.postForEntity("/api/event/join", body(i), Void::class.java)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        es.shutdown()
        assertEquals(10_000L, repo.count())
    }

    private fun body(i: Int): HttpEntity<JoinEventRequest> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val request = JoinEventRequest(userId = "user$i")
        return HttpEntity(request, headers)
    }
}