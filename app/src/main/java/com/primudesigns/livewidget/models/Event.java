package com.primudesigns.livewidget.models;

import android.os.Parcel;
import android.os.Parcelable;



public class Event implements Parcelable {

    private String status;
    private String challenge_type;
    private String start_timestamp;
    private String end_timestamp;
    private String description;
    private String title;
    private String url;
    private String cover_image;
    private String college;

    public Event() {
    }

    protected Event(Parcel in) {
        status = in.readString();
        challenge_type = in.readString();
        start_timestamp = in.readString();
        end_timestamp = in.readString();
        description = in.readString();
        title = in.readString();
        url = in.readString();
        cover_image = in.readString();
        college = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChallenge_type() {
        return challenge_type;
    }

    public void setChallenge_type(String challenge_type) {
        this.challenge_type = challenge_type;
    }

    public String getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(String start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    public String getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(String end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(challenge_type);
        parcel.writeString(start_timestamp);
        parcel.writeString(end_timestamp);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(cover_image);
        parcel.writeString(college);
    }
}
