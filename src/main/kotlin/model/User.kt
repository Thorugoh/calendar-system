package org.example.model

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Period(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
data class User(
    val name: String,
){
    var id: Int = 0;
    var meetings = setOf<Meeting>()

    var startDay = LocalTime.MIN
    var endDay = LocalTime.MAX


    constructor(name: String, id: Int): this(name) {
        this.id = id
    }

    // Should have a method to receive a scheduled meeting

    fun scheduleMeeting(guests: Set<User>, startTime: LocalDateTime, endTime: LocalDateTime): Meeting {
        return Meeting(participants = guests + this, startTime = startTime, endTime = endTime)
    }

    fun updateAvailableTime(startDay: LocalTime = this.startDay, endDay: LocalTime = this.endDay){
        this.startDay = startDay;
        this.endDay = endDay;
    }

    fun bestMeetingTimes(day: LocalDate, durationMinutes: Int): Set<Period>{
        val sortedMeetings = meetings.filter { it.startTime.toLocalDate() == day }.sortedBy { it.startTime }

        val bestMeetingTimes = mutableSetOf<Period>()
        var currentStart = LocalDateTime.of(day, startDay)

        if(meetings.isEmpty()) {
            return setOf(Period(LocalDateTime.of(day, startDay), LocalDateTime.of(day, endDay)))
        }

        for(meeting in sortedMeetings){
            val gapDuration = Duration.between(currentStart, meeting.startTime).toMinutes()
            if(gapDuration >= durationMinutes){
                bestMeetingTimes.add(Period(currentStart, currentStart.plusMinutes(gapDuration)))
            }
            currentStart = meeting.endTime
        }

        if(Duration.between(currentStart, LocalDateTime.of(day, endDay)).toMinutes() >= durationMinutes) {
            bestMeetingTimes.add(Period(currentStart, LocalDateTime.of(day, endDay
            )))
        }

        return bestMeetingTimes
    }
}
