package com.ourstory.assign_fcfs.controller

import com.ourstory.assign.common.ApiResponse
import com.ourstory.assign.dto.req.JoinEventRequest
import com.ourstory.assign.dto.res.FCFSDto
import com.ourstory.assign_fcfs.service.EventFCFSService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EventFCFSController(
    private val eventFCFSService: EventFCFSService
) {

    @PostMapping("/api/event/join")
    fun joinEvent(@RequestBody request: JoinEventRequest): ResponseEntity<ApiResponse<FCFSDto>> {
        val joinEvent = eventFCFSService.joinEvent(request.userId)

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    message = "이벤트 참여 성공",
                    data = joinEvent,
                )
            )
    }

}