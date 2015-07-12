package com.example.yeon.test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;


public class AsyncLoadEvent extends commonAsyncEvent{


    AsyncLoadEvent(EventSample activity) {
        super(activity);
    }

    @Override
    protected void doInBackground() throws IOException {

        DateTime now = new DateTime(System.currentTimeMillis());
        Events feed = mService.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        model.reset(feed.getItems());
    }

    static void run(EventSample eventSample) {
        new AsyncLoadEvent(eventSample).execute();
    }
}