package me.ausxh.scheduleGen

interface ScheduleGenerator {

    fun generate(eventGroups: List<List<Event>>) : List<List<Int>>
    fun validate(eventGroup: List<Event>) : Boolean

}
