package it.arisetech.app.arish.ui.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import butterknife.BindView;
import it.arisetech.app.arish.R;

public class ImageViewActivity extends BaseDrawerActivity{
    @BindView(R.id.imageViewActivity)
    ImageView imageView;
String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent i = getIntent();
        imageUrl= i.getStringExtra("image");
        try {
            UrlImageViewHelper.setUrlDrawable(imageView, imageUrl);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
