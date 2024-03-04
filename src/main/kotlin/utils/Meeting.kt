package org.example.utils

import org.example.data.MeetingEntity
import org.example.model.Meeting

fun Meeting.toEntity(): MeetingEntity {
    return MeetingEntity(this.id, this.endTime, this.startTime, this.participants.map {  it.toEntity()  }.toSet())
}

fun MeetingEntity.toModel(): Meeting {
    return Meeting(this.startTime, this.endTime, this.participants.map { it.toModel() }.toSet(), this.id)
}