package com.primudesigns.livewidget.widget;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.models.Event;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by sasik on 3/4/2017.
 */

public class widgetService extends RemoteViewsService {

    private ArrayList<Event> eventArrayList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return null;
    }


    public class listEventView implements RemoteViewsService.RemoteViewsFactory {

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            eventArrayList = new ArrayList<>();

            new loadData().execute();

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return eventArrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (position == AdapterView.INVALID_POSITION || eventArrayList == null) {
                return null;
            }

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_event_item);

            remoteViews.setTextViewText(R.id.tv_event_title, eventArrayList.get(position).getTitle());

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    private class loadData extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

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
            }

            if (Objects.equals(event1.getCollege(), "False") && Objects.equals(event1.getStatus(), "ONGOING")) {
                eventArrayList.add(1, event1);
            }

            if (Objects.equals(event2.getCollege(), "False") && Objects.equals(event2.getStatus(), "ONGOING")) {
                eventArrayList.add(2, event2);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

}
