package org.example.main

import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.data.Database
import org.example.data.MeetingDAO
import org.example.data.UserDAO
import org.example.model.Meeting
import org.example.model.User
import service.MeetingService
import java.time.LocalDateTime

//fun main() {
//    val manager = Database.getEntityManager()
//
////    val user = User("Bruno")
//    val userDAO = UserDAO(manager)
//    val bruno = userDAO.getById(1)
//    val victor = userDAO.getById(2)
////    userDAO.delete(1)
////    userDAO.delete(2)
////
////    userDAO.add(User("Victor"))
////    userDAO.add(User("Bruno"))
//
//    val meetingsDAO = MeetingDAO(manager);
////    println(meetingsDAO.getList())
////    meetingsDAO.add(Meeting(startTime = LocalDateTime.now(), endTime = LocalDateTime.now().plusHours(1), participants = userDAO.getList().toSet()))
//
//    val meeting = bruno.scheduleMeeting(setOf(victor), startTime = LocalDateTime.now().plusDays(2), endTime = LocalDateTime.now().plusDays(2).plusHours(2))
//    meetingsDAO.add(meeting)
//
//    val meetings = meetingsDAO.getList()
//    println(meetings)
//
//
//    manager.close()
//
//
//}

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson { setPrettyPrinting() }
        }

        val entityManager = Database.getEntityManager()
        val meetingDAO = MeetingDAO(entityManager)
        val userDAO = UserDAO(entityManager)

        routing {
            get("/meetings") {
                val user = userDAO.getById(1)
                call.respond(user.meetings)
            }

            post("/meetings") {
                val user = userDAO.getById(1)
                val meeting = call.receive<Meeting>()
                val meetingService = MeetingService(meetingDAO)
                val createdMeeting = meetingService.createMeeting(host = user, guests = meeting.participants, startTime = meeting.startTime, endTime = meeting.endTime)

                call.respond(HttpStatusCode.Created, createdMeeting)
            }

            get("/users") {
                val user = userDAO.getById(1)
                call.respond(user.meetings)
            }

            post("/users") {
                val user = userDAO.getById(1)
                val meeting = call.receive<Meeting>()
                val meetingService = MeetingService(meetingDAO)
                val createdMeeting = meetingService.createMeeting(host = user, guests = meeting.participants, startTime = meeting.startTime, endTime = meeting.endTime)

                call.respond(HttpStatusCode.Created, createdMeeting)
            }
        }
    }.start(wait = true)
}