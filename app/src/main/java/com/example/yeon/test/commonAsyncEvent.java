package com.example.yeon.test;

import android.os.AsyncTask;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import java.io.IOException;


abstract class commonAsyncEvent extends AsyncTask<Void, Void, Boolean> {

    final EventSample mActivity;
    final Eventmodel model;
    final com.google.api.services.calendar.Calendar mService;
//    private final View progressBar;

    commonAsyncEvent(EventSample activity) {
        mActivity = activity;
        mService = activity.mService;
        model =activity.model;
       // progressBar = activity.findViewById(R.id.title_refresh_progress);
    }

    @Override
    protected final Boolean doInBackground(Void... ignored) {
        try {
            doInBackground();
            return true;
        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            mActivity.showGooglePlayServicesAvailabilityErrorDialog(
                    availabilityException.getConnectionStatusCode());
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            mActivity.startActivityForResult(
                    userRecoverableException.getIntent(),
                    EventSample.REQUEST_AUTHORIZATION);
        } catch (IOException e) {
            mActivity.updateStatus("The following error occurred:\n" + e.getMessage());
//            Utils.logAndShow(activity, TaskSample.TAG, e);
        }
        return false;
    }

    @Override
    protected final void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if(success) {
            mActivity.refreshView();
        }
    }

    abstract protected void doInBackground() throws IOException;
}
