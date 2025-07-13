package com.ourstory.assign_fcfs.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event_fcfs")
class EventFCFSEntity(
    userId: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Version
    @Column(name = "version", nullable = false)
    var version: Int = 0

    @Column(name = "user_id", length = 64, nullable = false)
    var userId: String = userId

    @Column(name = "point", nullable = false)
    var point: Int = 0

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}