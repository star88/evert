package com.example.yeon.test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

/**
 * Created by yeon on 2015-07-12.
 */
public class Eventinfo implements Comparable<Eventinfo> ,Cloneable {

    String id;
    String summary;
    DateTime created;
    DateTime updated;
    String description;
    String location;
    String colorId;
    DateTime startdatetime;
    DateTime enddatetime;
    String attendees;


    Eventinfo(Event event){
        update(event);
    }

    void update(Event event) {
        id = event.getId();
        summary = event.getSummary();
        created = event.getCreated();
        updated = event.getUpdated();
        description = event.getDescription();
        location = event.getLocation();
        colorId = event.getColorId();
        startdatetime = event.getStart().getDateTime();
        if(startdatetime ==null){
            startdatetime = event.getStart().getDate();
        }
        enddatetime = event.getEnd().getDateTime();
        if(enddatetime ==null){
            enddatetime = event.getEnd().getDate();
        }
        attendees = event.getAttendees().toString();
    }
    @Override
    public int compareTo(Eventinfo other) {
        return summary.compareTo(other.summary);
    }

    @Override
    public Eventinfo clone() {
        try {
            return (Eventinfo) super.clone();
        } catch (CloneNotSupportedException exception) {
            // should not happen
            throw new RuntimeException(exception);
        }
    }

}
