package model

import org.example.model.Meeting
import org.example.model.Period
import org.example.model.User
import org.junit.jupiter.api.Test
import java.time.*
import kotlin.test.assertEquals

class UserTest {
    @Test
    fun testBestMeetingTimes_noMeetings_shouldBeFullDay(){
        val user = User("Victor")

        val day = LocalDate.of(2024, 3, 2)
        val result = user.bestMeetingTimes(day, 60)

        val expected = setOf(
            Period(LocalDateTime.of(day, LocalTime.MIN), LocalDateTime.of(day, LocalTime.MAX))
        )

        assertEquals(expected, result)
    }

    @Test
    fun testBestMeetingTimes_withMeetings() {
        val user = User("Victor")

        val day = LocalDate.of(2024, 3, 2)

        val morningMeeting = Meeting(LocalDateTime.of(day, LocalTime.of(9, 0)), LocalDateTime.of(day, LocalTime.of(10, 0)), setOf())
        val afternoonMeeting = Meeting(LocalDateTime.of(day, LocalTime.of(14, 0)), LocalDateTime.of(day, LocalTime.of(15, 0)), setOf())

        user.meetings = setOf(morningMeeting, afternoonMeeting)

        val result = user.bestMeetingTimes(day, 30)

        val expected = setOf(
            Period(LocalDateTime.of(day, LocalTime.MIN), LocalDateTime.of(day, LocalTime.of(9, 0))),
            Period(LocalDateTime.of(day, LocalTime.of(10, 0)), LocalDateTime.of(day, LocalTime.of(14, 0))),
            Period(LocalDateTime.of(day, LocalTime.of(15, 0)), LocalDateTime.of(day, LocalTime.MAX)),
        )

        assertEquals(expected, result)
    }

    @Test
    fun testBestMeetingTimes_withMeetingsAndAvailabilityTime() {
        val user = User("Victor").apply {
            startDay = LocalTime.of(8, 0)
            endDay = LocalTime.of(18, 0)
        }

        val day = LocalDate.of(2024, 3, 2)

        val morningMeeting = Meeting(LocalDateTime.of(day, LocalTime.of(9, 0)), LocalDateTime.of(day, LocalTime.of(10, 0)), setOf())
        val afternoonMeeting = Meeting(LocalDateTime.of(day, LocalTime.of(14, 0)), LocalDateTime.of(day, LocalTime.of(15, 0)), setOf())

        user.meetings = setOf(morningMeeting, afternoonMeeting)

        val result = user.bestMeetingTimes(day, 30)

        val expected = setOf(
            Period(LocalDateTime.of(day, user.startDay), LocalDateTime.of(day, LocalTime.of(9, 0))),
            Period(LocalDateTime.of(day, LocalTime.of(10, 0)), LocalDateTime.of(day, LocalTime.of(14, 0))),
            Period(LocalDateTime.of(day, LocalTime.of(15, 0)), LocalDateTime.of(day, user.endDay)),
        )

        assertEquals(expected, result)
    }
}