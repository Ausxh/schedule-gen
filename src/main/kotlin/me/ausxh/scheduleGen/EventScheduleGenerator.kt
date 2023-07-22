package me.ausxh.scheduleGen

import java.time.LocalTime

class EventScheduleGenerator : ScheduleGenerator {
    
    override fun generate(eventGroups: List<List<Event>>) : List<List<Int>> {
        var groups = mutableListOf<MutableList<Int>>()
        val eSize = eventGroups.size

        var numPerm = IntArray(eSize)

        // Assign the "values" for each list in reverse order (achieved by eSize -1 -i). The value will be equal to the product of the sizes of the lists below it. 
        // These values will be used to find the Cartesian Product
        for(i in eventGroups.indices) {
            numPerm[eSize - 1 - i] = if (i == 0) 1 else numPerm[eSize - i] * eventGroups[eSize - i].size
        }

        val groupToCheck = mutableListOf<Event>()
        val groupIndicies = mutableListOf<Int>()
        val totalPerm = numPerm[0] * eventGroups[0].size
        for(i in 0 until totalPerm) {
            var tempValue = i

            for(j in 0 until eSize) {
                groupToCheck.add(eventGroups[j][tempValue / numPerm[j]])
                groupIndicies.add(tempValue / numPerm[j])
                tempValue %= numPerm[j]
            }

            if (validate(groupToCheck))
                groups.add(groupIndicies.toMutableList())
           
            groupToCheck.clear()
            groupIndicies.clear()

        }
        
        return groups

    }

    override fun validate(eventGroup: List<Event>) : Boolean{
        for(i in 0 until eventGroup.size) {
            for(j in i+1 until eventGroup.size) {

                // Check if the days intersect and handle case where either set of days is null
                val sameDays: Boolean = eventGroup[i].days?.intersect(eventGroup[j].days ?: emptySet())?.isNotEmpty() ?: false

                val nullTime: Boolean = eventGroup[i].startTime == null || eventGroup[j].endTime == null || eventGroup[j].startTime == null || eventGroup[j].endTime == null
                val startConflict: Boolean = !nullTime && (eventGroup[i].startTime?.compareTo(eventGroup[j].endTime) ?: -1 <= 0 && eventGroup[i].endTime?.compareTo(eventGroup[j].endTime) ?: 1 >= 0)
                val endConflict: Boolean = !nullTime && (eventGroup[i].startTime?.compareTo(eventGroup[j].startTime) ?: -1 <= 0 && eventGroup[i].endTime?.compareTo(eventGroup[j].startTime) ?: 1 >= 0)

                if (sameDays && (startConflict || endConflict)) {
                    return false
                }
            }
        }

        return true
    }
}
