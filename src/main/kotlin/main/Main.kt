package org.example.main

import org.example.data.Database
import org.example.data.MeetingDAO
import org.example.data.UserDAO
import org.example.model.Meeting
import org.example.model.User
import java.time.LocalDateTime

fun main() {
    val manager = Database.getEntityManager()

//    val user = User("Bruno")
    val userDAO = UserDAO(manager)
    val bruno = userDAO.getById(4)
    val victor = userDAO.getById(3)
//    userDAO.delete(1)
//    userDAO.delete(2)
//
//    userDAO.add(User("Victor"))
//    userDAO.add(User("Bruno"))

    val meetingsDAO = MeetingDAO(manager);
//    println(meetingsDAO.getList())
//    meetingsDAO.add(Meeting(startTime = LocalDateTime.now(), endTime = LocalDateTime.now().plusHours(1), participants = userDAO.getList().toSet()))

    val meeting = bruno.scheduleMeeting(setOf(victor), startTime = LocalDateTime.now().plusDays(2), endTime = LocalDateTime.now().plusDays(2).plusHours(2))
    meetingsDAO.add(meeting)

    val meetings = meetingsDAO.getList()
    println(meetings)


    manager.close()


}