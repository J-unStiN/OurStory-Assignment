package com.ourstory.assign_fcfs.repository

import com.ourstory.assign_fcfs.domain.entity.EventFCFSEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventFCFSRepository : JpaRepository<EventFCFSEntity, Long> {
    fun existsByUserId(userId: String): Boolean
}