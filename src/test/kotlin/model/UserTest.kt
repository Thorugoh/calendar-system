package model

import org.example.model.Meeting
import org.example.model.Period
import org.example.model.User
import org.junit.jupiter.api.Test
import java.time.*
import kotlin.test.assertEquals

class UserTest {
    @Test
    fun `test findBestMeetingTime`() {
        // Create host and guest users
        val host = User("Host")
        val guest = User("Guest")

        // Set up some meetings for the host and guest
        host.meetings = setOf(
            Meeting(
                participants = setOf(host),
                startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
                endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0))
            ),
            Meeting(
                participants = setOf(host),
                startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)),
                endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0))
            )
        )

        guest.meetings = setOf(
            Meeting(
                participants = setOf(guest),
                startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)),
                endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0))
            ),
            Meeting(
                participants = setOf(guest),
                startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)),
                endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0))
            )
        )

        // Find the best meeting time between host and guest for a meeting of 30 minutes duration
        val bestMeetingTime = host.findBestMeetingTime(guest, 30)

        // Expected result: Start time at 10:30
        assertEquals(LocalTime.of(10, 30), bestMeetingTime?.startTime?.toLocalTime())

        // Expected result: End time at 11:00
        assertEquals(LocalTime.of(11, 0), bestMeetingTime?.endTime?.toLocalTime())
    }
}