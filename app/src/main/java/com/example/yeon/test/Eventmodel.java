package com.example.yeon.test;

import com.google.api.services.calendar.model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeon on 2015-07-12.
 */
public class Eventmodel {
    private final Map<String, Eventinfo> Events_map = new HashMap<String,Eventinfo>();

    int size(){
        synchronized (Events_map) {
            return Events_map.size();
        }
    }
    void remove(String id){
        synchronized (Events_map) {
            Events_map.remove(id);
        }
    }

    Eventinfo get(String id){
        synchronized (Events_map) {
            return Events_map.get(id);
        }
    }
    void add(Event eventToAdd){
        synchronized (Events_map) {
            Eventinfo found = get(eventToAdd.getId());
            if (found == null) {
                Events_map.put(eventToAdd.getId(), new Eventinfo(eventToAdd));
            } else {
                found.update(eventToAdd);
            }
        }
    }
    void reset(List<Event> events){
        synchronized (Events_map) {
            Events_map.clear();
            for (Event event : events) {
                add(event);
                //add(task.getTitle());
            }

        }
    }
    public Eventinfo[] toSortedArray(){
        synchronized (Events_map) {
            List<Eventinfo> result = new ArrayList<Eventinfo>();
            for (Eventinfo event : Events_map.values()) {
                result.add(event.clone());
            }
            Collections.sort(result);

            return result.toArray(new Eventinfo[0]);
        }
    }


}
