package com.ourstory.assign.exception

import com.ourstory.assign.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EventExceptionHandler {

    @ExceptionHandler(EventClosedException::class)
    fun handleEventClosed(e: EventClosedException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.GONE)
            .body(ApiResponse.error("이벤트가 종료되었습니다 (10,000명 초과)"))
    }

    @ExceptionHandler(DuplicateParticipationException::class)
    fun handleDuplicateParticipation(e: DuplicateParticipationException): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiResponse.error("이미 참여한 사용자입니다"))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ApiResponse<Void>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("서버 오류가 발생했습니다"))
    }

}