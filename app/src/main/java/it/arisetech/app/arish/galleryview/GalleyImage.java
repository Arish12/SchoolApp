package it.arisetech.app.arish.galleryview;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arish on 3/30/2017.
 */

public class GalleyImage implements Serializable{
    public GalleyImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String imageUrl;

    public GalleyImage() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
