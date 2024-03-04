package org.example.data

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "meetings")
class MeetingEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    @ManyToMany
    @JoinTable(
        name = "users_meetings",
        joinColumns = [JoinColumn(name = "meeting_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    val participants: Set<UserEntity> = hashSetOf(),
)