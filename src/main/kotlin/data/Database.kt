package org.example.data
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityManager
import javax.persistence.Persistence

object Database {
    fun getEntityManager(): EntityManager{
        val factory: EntityManagerFactory = Persistence.createEntityManagerFactory("mycalendar")
        return factory.createEntityManager()
    }
}