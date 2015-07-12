package com.example.yeon.test;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class EventSample extends Activity {

    com.google.api.services.calendar.Calendar mService;

    GoogleAccountCredential credential;
    private TextView mStatusText;
    private TextView mResultsText;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };

    static final int REQUEST_EVENT_ADD = 3;

    Eventmodel model;
    ListView eventlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mStatusText = (TextView) findViewById(R.id.result);
        mResultsText = (TextView) findViewById(R.id.state);
        Button insertpage = (Button) findViewById(R.id.insert);
        eventlist = (ListView) findViewById(R.id.eventlist);
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);

        credential = GoogleAccountCredential.usingOAuth2( getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();

        insertpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddorEditEventActivity.class);
                startActivityForResult(intent, REQUEST_EVENT_ADD);
            }
        });

        model = new Eventmodel();
    }

    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            refreshResults();
        } else {
            mStatusText.setText("Google Play Services required: " +
                    "after installing, close and relaunch this app.");
        }
    }


    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    mStatusText.setText("Account unspecified.");
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != RESULT_OK) {
                    chooseAccount();
                }
            case REQUEST_EVENT_ADD:
                if (resultCode == RESULT_OK) {

//                    Event event = new Event();
//                    event.setSummary(data.getStringExtra("title"));
//                    event.setDescription(data.getStringExtra("note"));
//
//                    Date date = new Date();
//                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//                    String dateAsISOString = df.format(date);
////                  DateTime startDateTime = new DateTime(dateAsISOString);

                    Event event = new Event()
                            .setSummary("Google I/O 2015")
                            .setLocation("800 Howard St., San Francisco, CA 94103")
                            .setDescription("A chance to hear more about Google's developer products.");

                    DateTime startDateTime = new DateTime("2015-05-28T09:00:00-07:00");
                    EventDateTime start = new EventDateTime()
                            .setDateTime(startDateTime)
                            .setTimeZone("America/Los_Angeles");
                    event.setStart(start);

                    DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
                    EventDateTime end = new EventDateTime()
                            .setDateTime(endDateTime)
                            .setTimeZone("America/Los_Angeles");
                    event.setEnd(end);

                    String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
                    event.setRecurrence(Arrays.asList(recurrence));

                    EventAttendee[] attendees = new EventAttendee[] {
                            new EventAttendee().setEmail("lpage@example.com"),
                            new EventAttendee().setEmail("sbrin@example.com"),
                    };
                    event.setAttendees(Arrays.asList(attendees));

                    EventReminder[] reminderOverrides = new EventReminder[] {
                            new EventReminder().setMethod("email").setMinutes(24 * 60),
                            new EventReminder().setMethod("sms").setMinutes(10),
                    };
                    Event.Reminders reminders = new Event.Reminders()
                            .setUseDefault(false)
                            .setOverrides(Arrays.asList(reminderOverrides));
                    event.setReminders(reminders);

                    String calendarId = "primary";
                    try {
                        event = mService.events().insert(calendarId, event).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("Event created: %s\n", event.getHtmlLink());

                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Attempt to get a set of data from the Google Calendar API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            if (isDeviceOnline()) {
                AsyncLoadEvent.run(this);
            } else {
                mStatusText.setText("No network connection available.");
            }
        }
    }

    public void clearResultsText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatusText.setText("Retrieving data…");
                mResultsText.setText("");
            }
        });
    }

    public void updateResultsText(final List<String> dataStrings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dataStrings == null) {
                    mStatusText.setText("Error retrieving data!");
                } else if (dataStrings.size() == 0) {
                    mStatusText.setText("No data found.");
                } else {
                    mStatusText.setText("Data retrieved using" +
                            " the Google Calendar API:");
                    mResultsText.setText(TextUtils.join("\n\n", dataStrings));
                }
            }
        });
    }


    public void updateStatus(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatusText.setText(message);
            }
        });
    }
    ArrayAdapter<Eventinfo> adapter;
    void refreshView() {
        adapter = new ArrayAdapter<Eventinfo>(this, R.layout.event_item, model.toSortedArray()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Event_item eventlayout = new Event_item(getApplicationContext());
                Eventinfo eventinfo = getItem(position);
                eventlayout.setEventid(eventinfo.id);
                eventlayout.setsummary(eventinfo.summary);
                eventlayout.setCreated(eventinfo.created);
                eventlayout.setUpdated(eventinfo.updated);
                eventlayout.setDescription(eventinfo.description);
                eventlayout.setLocation(eventinfo.location);
                eventlayout.setColorid(eventinfo.colorId);
                eventlayout.setStartDate(eventinfo.startdatetime);
                eventlayout.setEndDate(eventinfo.enddatetime);
                eventlayout.setAttendees(eventinfo.attendees);
                return eventlayout;
            }
        };
        eventlist.setAdapter(adapter);
    }

    private void chooseAccount() {
        startActivityForResult(
                credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode,
                        EventSample.this,
                        REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }



}
