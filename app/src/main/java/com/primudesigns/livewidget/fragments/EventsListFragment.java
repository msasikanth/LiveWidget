package com.primudesigns.livewidget.fragments;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.adapters.EventsAdapter;
import com.primudesigns.livewidget.database.EventContract;
import com.primudesigns.livewidget.models.Event;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Integer> {

    private static final int HTTP_OK = 200;
    private static final int HTTP_NOT_OK = 400;

    public static final String ACTION_DATA_UPDATED = "com.primudesigns.livewidget.ACTION_DATA_UPDATED";

    private ArrayList<Event> eventArrayList;
    private RecyclerView recyclerView;
    private TextView noConnection;
    private ProgressBar loading;

    public EventsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        int LOADER = 1;

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_events_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        noConnection = (TextView) view.findViewById(R.id.tv_no_connection);

        Bundle query = new Bundle();
        query.putString("query", getResources().getString(R.string.json_url));

        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<Integer> runtimeLoader = loaderManager.getLoader(LOADER);

        if (runtimeLoader == null) {
            loaderManager.initLoader(LOADER, query, this);
        } else {
            loaderManager.restartLoader(LOADER, query, this);
        }


        return view;

    }

    @Override
    public Loader<Integer> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<Integer>(getContext()) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) {
                    return;
                }

                forceLoad();
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            public Integer loadInBackground() {

                int result = 0;
                JSONObject object = null;
                HttpURLConnection urlConnection = null;

                String query;
                String jsonData = "";

                eventArrayList = new ArrayList<>();

                query = args.getString("query");

                if (query == null || TextUtils.isEmpty(query)) {
                    return null;
                }

                try {

                    URL url = new URL(query);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    int statusCode = urlConnection.getResponseCode();

                    if (statusCode == HTTP_OK) {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                        StringBuilder response = new StringBuilder();
                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            response.append(line).append("\n");
                            jsonData = response.toString();
                        }

                        Log.d("json", jsonData);

                        Event event = new Event();
                        event.setTitle(getResources().getString(R.string.title));
                        event.setDescription(getResources().getString(R.string.desc));
                        event.setStart_timestamp("Opens at : 20 Jan 2017, 12:00 AM IST");
                        event.setEnd_timestamp("Closes on : 26 Mar 2017, 11:59 PM IST");
                        event.setcover_image("https://hackerearth-media.global.ssl.fastly.net/media/hackathon/granular-deep-learning/images/baefd83a-c-hiring_granular-07%20%282%29.jpg");
                        event.setStatus("ONGOING");
                        event.setCollege("False");
                        event.setUrl("https://granular-deep-dive.hackerearth.com");

                        Event event1 = new Event();
                        event1.setTitle("Spark Streaming Innovation Contest");
                        event1.setDescription("Bring in your passion for Spark and Analytics.\nBuild a Spark Streaming Application and win $10,000!\n..");
                        event1.setStart_timestamp("Opens at : 08 Feb 2017, 12:00 AM AKDT'");
                        event1.setEnd_timestamp("Closes on : 1 Mar 2017, 11:59 PM AKDT");
                        event1.setcover_image("https://hackerearth-media.global.ssl.fastly.net/media/hackathon/spark-streaming-hackathon/images/049d6ebef1-hackathers-banner_8.jpg");
                        event1.setStatus("ONGOING");
                        event1.setCollege("False");
                        event1.setUrl("https://www.hackerearth.com/sprints/spark-streaming-hackathon/");

                        Event event2 = new Event();
                        event2.setTitle("L&T Infotech Fresher Hiring Challenge (2016 Batch)");
                        event2.setDescription("Must Read- Important form to be filled before the test.  !\nIs programming your passion? Are you wait..");
                        event2.setStart_timestamp("Opens at : 03 Mar 2017, 06:30 PM IST'");
                        event2.setEnd_timestamp("Closes on : 05 Mar 2017, 11:00 PM IST");
                        event2.setcover_image("https://hackerearth-media.global.ssl.fastly.net/media/hackathon/lt-infotech-fresher-hiring-challenge/images/cc6d1b6ee6-Hire_LT-07.jpg");
                        event2.setStatus("UPCOMING");
                        event2.setCollege("False");
                        event2.setUrl("https://www.hackerearth.com/challenge/hiring/lt-infotech-fresher-hiring-challenge/");


                        if (Objects.equals(event.getCollege(), "False") && Objects.equals(event.getStatus(), "ONGOING")) {
                            eventArrayList.add(0, event);
                            addEvent(event.getTitle(),
                                    event.getDescription(),
                                    event.getStatus(),
                                    event.getStart_timestamp(),
                                    event.getEnd_timestamp(),
                                    event.getUrl(),
                                    event.getcover_image(),
                                    event.getCollege());
                        }

                        if (Objects.equals(event1.getCollege(), "False") && Objects.equals(event1.getStatus(), "ONGOING")) {
                            eventArrayList.add(1, event1);
                            addEvent(event1.getTitle(),
                                    event1.getDescription(),
                                    event1.getStatus(),
                                    event1.getStart_timestamp(),
                                    event1.getEnd_timestamp(),
                                    event1.getUrl(),
                                    event1.getcover_image(),
                                    event1.getCollege());
                        }

                        if (Objects.equals(event2.getCollege(), "False") && Objects.equals(event2.getStatus(), "ONGOING")) {
                            eventArrayList.add(2, event2);
                            addEvent(event1.getTitle(),
                                    event2.getDescription(),
                                    event2.getStatus(),
                                    event2.getStart_timestamp(),
                                    event2.getEnd_timestamp(),
                                    event2.getUrl(),
                                    event2.getcover_image(),
                                    event2.getCollege());
                        }

                        result = HTTP_OK;

                    } else {
                        result = HTTP_NOT_OK;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return result;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {

        loading.setVisibility(View.INVISIBLE);

        if (data == HTTP_OK) {

            EventsAdapter adapter = new EventsAdapter(getActivity(), eventArrayList);
            recyclerView.setAdapter(adapter);

            updateWidget(getActivity());

        } else {
            if (recyclerView != null) {
                noConnection.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }


    private void addEvent(String title, String desc, String status, String start_timestamp, String end_timestamp, String url, String cover_image, String college) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EventContract.EventEntry.COLUMN_TITLE, title);
        contentValues.put(EventContract.EventEntry.COLUMN_DESCRIPTION, desc);
        contentValues.put(EventContract.EventEntry.COLUMN_STATUS, status);
        contentValues.put(EventContract.EventEntry.COLUMN_START_TIMESTAMP, start_timestamp);
        contentValues.put(EventContract.EventEntry.COLUMN_END_TIMESTAMP, end_timestamp);
        contentValues.put(EventContract.EventEntry.COLUMN_URL, url);
        contentValues.put(EventContract.EventEntry.COLUMN_COVER_IMAGE, cover_image);
        contentValues.put(EventContract.EventEntry.COLUMN_COLLEGE, college);

        getContext().getContentResolver().insert(EventContract.EventEntry.CONTENT_URI, contentValues);
    }

    private void updateWidget(Context context) {
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

}
