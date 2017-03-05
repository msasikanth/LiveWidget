package com.primudesigns.livewidget.adapters;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.database.EventContract;
import com.primudesigns.plaid.components.AspectImageView;
import com.primudesigns.plaid.components.BaselineGridTextView;
import com.squareup.picasso.Picasso;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private Activity context;
    private Cursor cursor;

    public FavoritesAdapter(Activity context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {

        cursor.moveToPosition(position);

        holder.title.setText(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.COLUMN_TITLE)));
        holder.description.setText(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.COLUMN_DESCRIPTION)));
        holder.startDate.setText(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.COLUMN_START_TIMESTAMP)));
        holder.endDate.setText(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.COLUMN_END_TIMESTAMP)));
        holder.status.setText(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.COLUMN_STATUS)));

        Picasso.with(context)
                .load(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.COLUMN_COVER_IMAGE)))
                .into(holder.cover);



    }

    public String getID(int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(EventContract.EventEntry._ID));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private BaselineGridTextView title;
        private BaselineGridTextView description;
        private BaselineGridTextView startDate;
        private BaselineGridTextView endDate;
        private BaselineGridTextView status;

        private AspectImageView cover;
        private ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_title);
            description = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_desc);
            startDate = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_start);
            endDate = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_end);
            status = (BaselineGridTextView) itemView.findViewById(R.id.tv_event_status);

            cover = (AspectImageView) itemView.findViewById(R.id.iv_event_banner);

        }
    }
}
