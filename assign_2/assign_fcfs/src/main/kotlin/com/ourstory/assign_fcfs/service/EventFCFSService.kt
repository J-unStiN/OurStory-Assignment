package com.ourstory.assign_fcfs.service

import com.ourstory.assign.dto.res.FCFSDto
import com.ourstory.assign.exception.DuplicateParticipationException
import com.ourstory.assign.exception.EventClosedException
import com.ourstory.assign_fcfs.domain.entity.EventFCFSEntity
import com.ourstory.assign_fcfs.repository.EventFCFSRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventFCFSService(

    private val eventFCFSRepository: EventFCFSRepository
) {

    companion object {
        private const val MAX_PARTICIPANTS = 10000L
    }

    @Transactional
    fun joinEvent(userId: String) : FCFSDto {
        if (eventFCFSRepository.existsByUserId(userId)) {
            throw DuplicateParticipationException("이미 이벤트에 참여하셨습니다. 중복 참여는 불가능합니다.")
        }

        val saveEntity = eventFCFSRepository.save(EventFCFSEntity(userId))
        val rank: Long? = saveEntity.id

        if (rank == null || rank > MAX_PARTICIPANTS) {
            throw EventClosedException("이벤트 참여 인원이 초과되었습니다.")
        }

        saveEntity.point = calculatePoint(rank)

        return FCFSDto.of(saveEntity)
    }

    private fun calculatePoint(rank: Long): Int {
        return when {
            rank <= 100 -> 100000
            rank <= 2000 -> 50000
            rank <= 5000 -> 20000
            rank <= 10000 -> 10000
            else -> 0
        }
    }
}