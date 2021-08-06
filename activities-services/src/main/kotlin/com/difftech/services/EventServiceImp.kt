package com.difftech.services

import com.difftech.models.Event
import java.util.*

interface EventService {
    fun addEvent(event: Event): Boolean
    fun cancelEvent(eventId: UUID): Boolean
}

class EventServiceImp(val events: MutableList<Event> = mutableListOf()) : EventService {
    override fun addEvent(event: Event): Boolean = events.add(event)

    override fun cancelEvent(eventId: UUID): Boolean = events.remove(events.find { it.eventId == eventId })
}