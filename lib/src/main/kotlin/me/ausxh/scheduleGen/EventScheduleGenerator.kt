package me.ausxh.scheduleGen

import java.time.LocalTime

class EventScheduleGenerator : ScheduleGenerator {
    
    override fun generate(eventGroups: List<List<Event>>) : MutableList<MutableList<Event>> {
        var groups = mutableListOf<MutableList<Event>>()
        val eSize = eventGroups.size

        var numPerm = IntArray(eSize)

        // Assign the "values" for each list in reverse order (achieved by eSize -1 -i). The value will be equal to the product of the sizes of the lists below it. 
        // These values will be used to find the Cartesian Product
        for(i in eventGroups.indices) {
            numPerm[eSize - 1 - i] = if (i == 0) 1 else numPerm[eSize - i] * eventGroups[eSize - i].size
        }

        val groupToCheck = mutableListOf<Event>()
        val totalPerm = numPerm[0] * eventGroups[0].size
        for(i in 0 until totalPerm) {
            var tempValue = i

            for(j in 0 until eSize) {
                groupToCheck.add(eventGroups[j][tempValue / numPerm[j]])
                tempValue %= numPerm[j]
            }

            if (validate(groupToCheck))
                groups.add(groupToCheck.toMutableList())
           
            groupToCheck.clear()

        }
        
        return groups

    }

    override fun validate(eventGroup: List<Event>) : Boolean{
        for(i in 0 until eventGroup.size) {
            for(j in i+1 until eventGroup.size) {

                val sameDays: Boolean = eventGroup[i].days.intersect(eventGroup[j].days).isNotEmpty()
                val startConflict: Boolean = eventGroup[i].startTime.compareTo(eventGroup[j].endTime) <= 0 && eventGroup[i].endTime.compareTo(eventGroup[j].endTime) >= 0
                val endConflict: Boolean = eventGroup[i].startTime.compareTo(eventGroup[j].startTime) <= 0 && eventGroup[i].endTime.compareTo(eventGroup[j].startTime) >= 0

                if (sameDays && (startConflict || endConflict)) {
                    return false
                }
            }
        }

        return true
    }
}
