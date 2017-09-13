package it.arisetech.app.arish.eventview;

import java.util.ArrayList;

/**
 * Created by Arish on 2/20/2017.
 */

public class EventsView {
    public String imageTitle;
    public String imageUrl;

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getEventView() {
        return eventView;
    }

    public void setEventView(ArrayList<String> eventView) {
        this.eventView = eventView;
    }

    public ArrayList<String> eventView;
}
