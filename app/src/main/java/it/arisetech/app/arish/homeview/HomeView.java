package it.arisetech.app.arish.homeview;

import java.util.ArrayList;

/**
 * Created by Arish on 2/26/2017.
 */

public class HomeView {
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String imageTitle;
    public String imageUrl;
    public int likesCount;
    public boolean isLiked;

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public HomeView() {

    }

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
