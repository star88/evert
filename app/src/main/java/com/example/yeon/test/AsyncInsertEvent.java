package com.example.yeon.test;

import java.io.IOException;

public class AsyncInsertEvent extends commonAsyncEvent {
    private EventSample mActivity;

    AsyncInsertEvent(EventSample activity) {
        super(activity);
    }

    @Override
    protected void doInBackground() throws IOException {

    }

    static void run(EventSample eventSample) {
        new AsyncLoadEvent(eventSample).execute();
    }

}
