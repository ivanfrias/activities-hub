package com.difftech.models

import com.difftech.services.EventServiceImp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class EventTests {
    @Test
    @DisplayName("should be able to create events")
    fun shouldCreateEvents() {
        val eventService = EventServiceImp()
        eventService.addEvent(Event())
        Assertions.assertEquals(eventService.events.size, 1)
    }
}