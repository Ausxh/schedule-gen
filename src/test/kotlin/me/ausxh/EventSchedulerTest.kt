package me.ausxh.scheduleGen

import java.time.LocalDate
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

val gen: ScheduleGenerator= EventScheduleGenerator()
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mma")

val time1: LocalTime = LocalTime.parse("01:30PM", timeFormatter)
val time2: LocalTime = LocalTime.parse("02:30PM", timeFormatter)
val time3: LocalTime = LocalTime.parse("03:30PM", timeFormatter)
val time4: LocalTime = LocalTime.parse("04:30PM", timeFormatter)
val time5: LocalTime = LocalTime.parse("05:30PM", timeFormatter)

val event1: Event = Event(LocalDate.now(), LocalDate.now(), setOf<DayOfWeek>(DayOfWeek.MONDAY), time2, time4)
val event2: Event = Event(LocalDate.now(), LocalDate.now(), setOf<DayOfWeek>(DayOfWeek.MONDAY), null, null)
val event3: Event = Event(LocalDate.now(), LocalDate.now(), setOf<DayOfWeek>(DayOfWeek.TUESDAY), time1, time3)
val event4: Event = Event(LocalDate.now(), LocalDate.now(), setOf<DayOfWeek>(DayOfWeek.MONDAY), time1, time3)
val event5: Event = Event(LocalDate.now(), LocalDate.now(), setOf<DayOfWeek>(DayOfWeek.MONDAY), time3, time5)

class LibraryTest {
    @Test fun firstNullTime() {
        assertTrue(gen.validate(listOf<Event>(event2, event1)))
    }

    @Test fun secondNullTime() {
        assertTrue(gen.validate(listOf<Event>(event1, event2)))
    }

    @Test fun sameTimeAndDay() {
        assertFalse(gen.validate(listOf<Event>(event1, event1)))
    }

    @Test fun differentDays() {
        assertTrue(gen.validate(listOf<Event>(event1, event3)))
    }

    @Test fun startConflict() {
        assertFalse(gen.validate(listOf<Event>(event1, event4)))
    }

    @Test fun endConflict() {
        assertFalse(gen.validate(listOf<Event>(event1, event5)))
    }

}
