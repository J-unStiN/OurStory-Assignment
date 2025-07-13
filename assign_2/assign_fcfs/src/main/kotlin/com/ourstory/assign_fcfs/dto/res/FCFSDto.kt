package com.ourstory.assign.dto.res

import com.ourstory.assign_fcfs.domain.entity.EventFCFSEntity
import java.time.LocalDateTime

data class FCFSDto(
    val userId: String,
    val rank: Long?,
    val point: Int,
    val createdAt: LocalDateTime
) {
    companion object {
        fun of(fcfs: EventFCFSEntity): FCFSDto {
            return FCFSDto(
                userId = fcfs.userId,
                rank = fcfs.id,
                point = fcfs.point,
                createdAt = fcfs.createdAt
            )
        }
    }
}
