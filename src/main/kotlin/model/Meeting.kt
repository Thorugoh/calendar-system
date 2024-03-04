package org.example.model
import java.time.LocalDateTime;
data class Meeting(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    var participants: Set<User>,
) {
    var id: Int = 0;
    constructor(startTime: LocalDateTime, endTime: LocalDateTime, participants: Set<User>, id: Int): this(startTime, endTime, participants) {
        this.id = id
    }
}