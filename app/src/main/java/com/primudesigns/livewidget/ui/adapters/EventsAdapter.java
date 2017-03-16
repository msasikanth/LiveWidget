package com.primudesigns.livewidget.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.data.EventContract;
import com.primudesigns.livewidget.data.EventContract.EventEntry;
import com.primudesigns.livewidget.models.Event;
import com.primudesigns.plaid.components.AspectImageView;
import com.primudesigns.plaid.components.BaselineGridTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Event> event;

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {

            String url = event.get(position).getUrl();

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(context.getResources().getColor(R.color.colorAccent));
            customTabsIntent.launchUrl(context, Uri.parse(url));

        }
    };

//    private OnItemClickListener onFavClickListener = new OnItemClickListener() {
//        @Override
//        public void onClick(View view, int position) {
//
//            if (!Exists(event.get(position).getTitle())) {
//
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(EventEntry.COLUMN_TITLE, event.get(position).getTitle());
//                contentValues.put(EventEntry.COLUMN_DESCRIPTION, event.get(position).getDescription());
//                contentValues.put(EventEntry.COLUMN_STATUS, event.get(position).getStatus());
//                contentValues.put(EventEntry.COLUMN_START_TIMESTAMP, event.get(position).getStart_timestamp());
//                contentValues.put(EventEntry.COLUMN_END_TIMESTAMP, event.get(position).getEnd_timestamp());
//                contentValues.put(EventEntry.COLUMN_URL, event.get(position).getUrl());
//                contentValues.put(EventEntry.COLUMND_COVER_IMAGE, event.get(position).getcover_image());
//
//                context.getContentResolver().insert(EventEntry.CONTENT_URI, contentValues);
//
//                Utils.showSnackBar(context, R.id.container, "Pressed Favorite", Snackbar.LENGTH_LONG);
//
//            } else {
//
//                String id = event.get(position).getTitle();
//
//                context.getContentResolver().delete(EventContract.EventEntry.makeUriForStock(id), null, null);
//                Utils.showSnackBar(context, R.id.container, "Item Removed", Snackbar.LENGTH_LONG);
//
//
//            }
//        }
//    };

    public EventsAdapter(Activity context, ArrayList<Event> event) {
        this.context = context;
        this.event = event;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final EventsAdapter.ViewHolder holder, int position) {

        holder.title.setText(event.get(position).getTitle());
        holder.description.setText(event.get(position).getDescription());
        holder.startDate.setText(event.get(position).getstartTimestamp());
        holder.endDate.setText(event.get(position).getendTimestamp());
        holder.status.setText("STATUS : " + event.get(position).getStatus());

        if (event.get(position).getcoverImage() != null && !TextUtils.isEmpty(event.get(position).getcoverImage())) {

            Picasso.with(context)
                    .load(event.get(position).getcoverImage())
                    .placeholder(context.getDrawable(R.drawable.placeholder))
                    .into(holder.cover);
        }

    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private BaselineGridTextView title;
        private BaselineGridTextView description;
        private BaselineGridTextView startDate;
        private BaselineGridTextView endDate;
        private BaselineGridTextView status;

        private AspectImageView cover;

        private OnItemClickListener onItemClickListener;
        private OnItemClickListener onFavClickListener;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;

            title = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_title);
            description = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_desc);
            startDate = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_start);
            endDate = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_end);
            status = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_status);

            cover = (AspectImageView) itemView.findViewById(R.id.iv_event_banner);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
//
//            fav.setClickable(true);
//            fav.setOnClickListener(onFav);

        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(itemView, getAdapterPosition());
            }
        }

        //TODO 4: Add Favorite click action for future

//        public View.OnClickListener onFav = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (onFavClickListener != null) {
//                    onFavClickListener.onClick(view, getAdapterPosition());
//                }
//
//            }
//        };

    }

    public boolean Exists(String searchItem) {

        String[] columns = {EventEntry.COLUMN_TITLE};
        String selection = EventEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = context.getContentResolver().query(EventContract.EventEntry.CONTENT_URI, columns, selection, selectionArgs, EventEntry.COLUMN_TITLE);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;

    }

}
