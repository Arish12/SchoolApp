package it.arisetech.app.arish.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import it.arisetech.app.arish.R;
import it.arisetech.app.arish.eventview.EventsView;
import it.arisetech.app.arish.eventview.EventsViewAdapter;
import it.arisetech.app.arish.galleryview.GalleyImage;
import it.arisetech.app.arish.galleryview.GridViewImageAdapter;
import it.arisetech.app.arish.galleryview.SliderDialogFragment;
import it.arisetech.app.arish.ui.utils.CircleTransformation;
import it.arisetech.app.arish.ui.view.FeedContextMenuManager;
import it.arisetech.app.arish.ui.view.RevealBackgroundView;

/**
 * Created by Miroslaw Stanek on 14.01.15.
 */
public class EventsActivity extends BaseDrawerActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private static final String eventsUrl = "http://www.feather-techsolution.com/schol/events.php";

    //Creating Views
    private ArrayList<GalleyImage> eventView;
    private ProgressDialog pDialog;
    private GridViewImageAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @BindView(R.id.content)
    CoordinatorLayout content;
    @BindView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @BindView(R.id.events_recycler_view)
    RecyclerView evRecyclerView;

    @BindView(R.id.tlUserProfileTabs)
    TabLayout tlUserProfileTabs;

    @BindView(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;

    @BindView(R.id.vUserProfileRoot)
    View vUserProfileRoot;


    private int avatarSize;
    private String profilePhoto;

    ProgressBar progressBar;



    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, EventsActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
//       pDialog = new ProgressDialog(this);
        eventView = new ArrayList<>();
        mAdapter = new GridViewImageAdapter(getApplicationContext(), eventView);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        evRecyclerView.setLayoutManager(mLayoutManager);
        evRecyclerView.setItemAnimator(new DefaultItemAnimator());
        evRecyclerView.setAdapter(mAdapter);
        evRecyclerView.addOnItemTouchListener(new GridViewImageAdapter.RecyclerTouchListener(getApplicationContext(),
                evRecyclerView, new GridViewImageAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images",  eventView);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SliderDialogFragment newFragment = SliderDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        setupRevealBackground(savedInstanceState);
        getData();
    }



    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
//            userPhotosAdapter.setLockedAnimations(true);
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            evRecyclerView.setVisibility(View.VISIBLE);
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
//            eventsViewAdapter = new EventsViewAdapter(this);
//            rvUserProfile.setAdapter(eventsViewAdapter);
            setupEvent();
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            evRecyclerView.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

    private void animateUserProfileOptions() {
        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
           vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
           ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
//           vUserDetails.setTranslationY(-vUserDetails.getHeight());

           vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
           ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
//           vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);

    }
    private void setupEvent(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
//            @Override
//            protected int getExtraLayoutSpace(RecyclerView.State state) {
//                return 300;
//            }
//        };
//        evRecyclerView.setLayoutManager(linearLayoutManager);
////
//        eventView = new ArrayList<>();
//
//
////        feedAdapter = new HomeViewAdapter(this);
////        feedAdapter.setOnFeedItemClickListener(this);
//       evRecyclerView.setAdapter(eventsViewAdapter);
        evRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
    }
private void getData(){
//    pDialog.setMessage("Wait...");
//    pDialog.show();

    JsonArrayRequest req = new JsonArrayRequest(eventsUrl,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("+++++++", response.toString());


//                        images.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject  object = response.getJSONObject(i);
                            GalleyImage galleyImage = new GalleyImage();
                            galleyImage.setImageUrl(object.getString("image"));

                            eventView.add(galleyImage);
//
//                                json = array.getJSONObject(i);
////                geteventAdapter.setImageTitle(json.getString("title"));
//                                geteventAdapter.setImage(json.getString("image"));
//                                geteventAdapter.setTitle(json.getString("title"));
//

                        } catch (JSONException e) {
                            Log.e("++++++++", "Json parsing error: " + e.getMessage());
                            Snackbar snackbar = Snackbar
                                    .make(content, "Server connection Failed!", Snackbar.LENGTH_LONG)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    });

// Changing message text color
                            snackbar.setActionTextColor(Color.WHITE);

// Changing snackbar background color
                            ViewGroup group = (ViewGroup) snackbar.getView();
                            group.setBackgroundColor(ContextCompat.getColor(EventsActivity.this, R.color.btn_default_shadow));

// Changing action button text color
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);

                            snackbar.show();


                        }
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Log.e("++++++++", "Error: " + error.getMessage());
            Snackbar snackbar = Snackbar
                    .make(content, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

// Changing message text color
            snackbar.setActionTextColor(Color.WHITE);

// Changing snackbar background color
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(EventsActivity.this, R.color.btn_default_shadow));

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);

            snackbar.show();



//            pDialog.hide();
        }
    });
    //Creating request queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    //Adding request to the queue
    requestQueue.add(req);
}

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(EventsActivity.this);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setMessage("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })

                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

    }
}
