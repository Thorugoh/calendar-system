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

    private fun receiveScheduledMeeting(meeting: Meeting): Set<Meeting> {
        meetings += meeting
        return meetings
    }

    fun scheduleMeeting(guests: Set<User>, startTime: LocalDateTime, endTime: LocalDateTime): Meeting {
        val meeting = Meeting(participants = guests + this, startTime = startTime, endTime = endTime)

        receiveScheduledMeeting(meeting)
        guests.forEach{ guest ->
            guest.receiveScheduledMeeting(meeting)
        }

        return meeting
    }

    fun updateAvailableTime(startDay: LocalTime = this.startDay, endDay: LocalTime = this.endDay){
        this.startDay = startDay;
        this.endDay = endDay;
    }

     fun findAvailableSlots(day: LocalDate, durationMinutes: Long): Set<Period> {
        val sortedMeetings = meetings.filter { it.startTime.toLocalDate() == day }.sortedBy { it.startTime }

        val bestMeetingTimes = mutableSetOf<Period>()
        var currentStart = LocalDateTime.of(day, startDay)

        if (meetings.isEmpty()) {
            return setOf(Period(LocalDateTime.of(day, startDay), LocalDateTime.of(day, endDay)))
        }

        for (meeting in sortedMeetings) {
            val gapDuration = Duration.between(currentStart, meeting.startTime).toMinutes()
            if (gapDuration >= durationMinutes) {
                bestMeetingTimes.add(Period(currentStart, currentStart.plusMinutes(gapDuration)))
            }
            currentStart = meeting.endTime
        }

        if (Duration.between(currentStart, LocalDateTime.of(day, endDay)).toMinutes() >= durationMinutes) {
            bestMeetingTimes.add(Period(currentStart, LocalDateTime.of(day, endDay)))
        }

        return bestMeetingTimes
    }

    // Function to find the best meeting time for both host and guest
    fun findBestMeetingTime(guest: User, durationMinutes: Long): Period? {
        val commonFreeSlots = mutableSetOf<Period>()

        val hostFreeSlots = this.findAvailableSlots(LocalDate.now(), durationMinutes)
        val guestFreeSlots = guest.findAvailableSlots(LocalDate.now(), durationMinutes)

        for (hostSlot in hostFreeSlots) {
            for (guestSlot in guestFreeSlots) {
                val commonStart = if (hostSlot.startTime.isAfter(guestSlot.startTime)) hostSlot.startTime else guestSlot.startTime
                val commonEnd = if (hostSlot.endTime.isBefore(guestSlot.endTime)) hostSlot.endTime else guestSlot.endTime
                val commonDuration = Duration.between(commonStart, commonEnd).toMinutes()
                if (commonDuration >= durationMinutes) {
                    commonFreeSlots.add(Period(commonStart, commonEnd))
                }
            }
        }

        return commonFreeSlots.minByOrNull { it.startTime }
    }
}
