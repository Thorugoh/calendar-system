package service

import org.example.data.MeetingDAO
import org.example.model.Meeting
import org.example.model.User
import java.time.LocalDateTime

class MeetingService(private val meetingDAO: MeetingDAO) {
    fun createMeeting(host: User, guests: Set<User>, startTime: LocalDateTime, endTime: LocalDateTime): Meeting {
        val meeting = host.scheduleMeeting(guests, startTime, endTime)
        meetingDAO.add(meeting)

        return meeting;
    }
}