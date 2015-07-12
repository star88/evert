package com.example.yeon.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.client.util.DateTime;

/**
 * Created by yeon on 2015-07-12.
 */
public class Event_item extends LinearLayout{
    Context mcontext;
    LayoutInflater inflater;

    TextView eventid;
    TextView eventsummary;
    TextView created;
    TextView updated;
    TextView description;
    TextView location;
    TextView colorid;
    TextView startDate;
    TextView endDate;
    TextView attendees;

    public Event_item(Context context) {
        super(context);
        this.mcontext = context;
        init();
    }

    public Event_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
        init();
    }

    private void init(){
        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.event_item,this,true);

        eventid = (TextView) findViewById(R.id.eventid);
        eventsummary = (TextView) findViewById(R.id.eventsummary);
        created = (TextView) findViewById(R.id.created);
        updated = (TextView) findViewById(R.id.updated);
        description = (TextView) findViewById(R.id.description);
        location = (TextView) findViewById(R.id.location);
        colorid = (TextView) findViewById(R.id.colorid);
        startDate = (TextView) findViewById(R.id.startdate);
        endDate = (TextView) findViewById(R.id.enddate);
        attendees = (TextView) findViewById(R.id.attendees);
    }

    public void setEventid(String eventid) {
        try {
            this.eventid.setText(eventid);
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setsummary(String eventsummary) {
        try {
            this.eventsummary.setText(eventsummary);
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setCreated(DateTime created) {
        try {
            this.created.setText(created.toString());
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setUpdated(DateTime updated) {
        try {
            this.updated.setText(updated.toString());
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setDescription(String description) {
        try {
            this.description.setText(description);
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setLocation(String location) {
        try {
            this.location.setText(location);
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setColorid(String colorid) {
        try {
            this.colorid.setText(colorid);
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setStartDate(DateTime startDate) {
        try {
            this.colorid.setText(startDate.toString());
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setEndDate(DateTime endDate) {
        try {
            this.endDate.setText(endDate.toString());
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }

    public void setAttendees(String attendees) {
        try {
            this.attendees.setText(attendees);
        }catch(NullPointerException e){
            Log.e("Event_itemLayout", e.toString());
        }
    }
}
