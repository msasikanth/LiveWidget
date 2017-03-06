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
import com.primudesigns.livewidget.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Integer> {

    private static final int HTTP_OK = 200;
    private static final int HTTP_NOT_OK = 400;
    private static final String PARAM = "-utf";

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
        query.putString("query", getResources().getString(R.string.json_url) + PARAM);

        //TODO 6 : Change to Retrofit for more streamlined DATA FETCHING

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

                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        StringBuilder response = new StringBuilder();
                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            response.append(line).append("\n");
                        }

                        jsonData = response.toString();

                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray responseArray = jsonObject.getJSONArray("response");

                        for (int i = 0; i < responseArray.length(); i++) {

                            JSONObject item = responseArray.optJSONObject(i);

                            Event event = new Event();

                            event.setTitle(item.getString(Constants.TITLE));
                            event.setDescription(item.getString(Constants.DESCRIPTION));
                            event.setStart_timestamp(item.getString(Constants.START_TIME));
                            event.setEnd_timestamp(item.getString(Constants.END_TIME));
                            event.setStatus(item.getString(Constants.STATUS));
                            event.setCollege(item.getString(Constants.COLLEGE));
                            event.setUrl(item.getString(Constants.URL));

                            if (Objects.equals(event.getCollege(), "false") && Objects.equals(event.getStatus(), "ONGOING")) {
                                eventArrayList.add(event);
                                addEvent(event.getTitle(),
                                        event.getDescription(),
                                        event.getStatus(),
                                        event.getStart_timestamp(),
                                        event.getEnd_timestamp(),
                                        event.getUrl(),
                                        event.getCollege());
                            }
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


    private void addEvent(String title, String desc, String status, String start_timestamp, String end_timestamp, String url, String college) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EventContract.EventEntry.COLUMN_TITLE, title);
        contentValues.put(EventContract.EventEntry.COLUMN_DESCRIPTION, desc);
        contentValues.put(EventContract.EventEntry.COLUMN_STATUS, status);
        contentValues.put(EventContract.EventEntry.COLUMN_START_TIMESTAMP, start_timestamp);
        contentValues.put(EventContract.EventEntry.COLUMN_END_TIMESTAMP, end_timestamp);
        contentValues.put(EventContract.EventEntry.COLUMN_URL, url);
        contentValues.put(EventContract.EventEntry.COLUMN_COLLEGE, college);

        getContext().getContentResolver().insert(EventContract.EventEntry.CONTENT_URI, contentValues);
    }

    private void updateWidget(Context context) {
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

}
