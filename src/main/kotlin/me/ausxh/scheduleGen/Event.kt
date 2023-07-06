package me.ausxh.scheduleGen

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.LocalTime;

open class Event(var startDate: LocalDate, var endDate: LocalDate, var days: Set<DayOfWeek>, var startTime: LocalTime, var endTime: LocalTime)

