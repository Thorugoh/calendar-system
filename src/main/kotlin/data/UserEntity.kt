package org.example.data

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    val name: String = "",
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @ManyToMany(mappedBy = "participants")
    val meetings: Set<MeetingEntity> = hashSetOf()
)