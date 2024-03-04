package org.example.data

import org.example.model.Meeting
import org.example.utils.toEntity
import org.example.utils.toModel
import javax.persistence.EntityManager

class MeetingDAO(manager: EntityManager): DAO<Meeting, MeetingEntity>(manager, MeetingEntity::class.java) {
    override fun toEntity(meeting: Meeting): MeetingEntity {
        return meeting.toEntity()
    }

    override fun toModel(entity: MeetingEntity): Meeting {
        return entity.toModel().apply {
            participants = entity.participants.map { it.toModel() }.toSet()
        }
    }
}