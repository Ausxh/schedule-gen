package me.ausxh.scheduleGen

interface ScheduleGenerator {

    fun generate(eventGroups: List<List<Event>>) : List<List<Event>>
    fun validate(eventGroup: List<Event>) : Boolean

}
