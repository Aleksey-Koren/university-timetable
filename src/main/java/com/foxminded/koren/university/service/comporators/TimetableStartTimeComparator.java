package com.foxminded.koren.university.service.comporators;

import java.util.Comparator;

import com.foxminded.koren.university.entity.interfaces.TimetableEvent;

public class TimetableStartTimeComparator implements Comparator<TimetableEvent> {

    @Override
    public int compare(TimetableEvent o1, TimetableEvent o2) {
        if(o1.getStartTime().isBefore(o2.getStartTime())) {
            return 1;
        }else if(o1.getStartTime().isAfter(o2.getStartTime())) {
            return -1; 
        }else {
            return 0;
        }
    }
}