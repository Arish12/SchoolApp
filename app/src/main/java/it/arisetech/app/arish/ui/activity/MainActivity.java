package it.arisetech.app.arish.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import it.arisetech.app.arish.R;
import it.arisetech.app.arish.Utils;
import it.arisetech.app.arish.homeview.FeedAdapter;
import it.arisetech.app.arish.homeview.FeedItemAnimator;
import it.arisetech.app.arish.ui.view.FeedContextMenu;
import it.arisetech.app.arish.ui.view.FeedContextMenuManager;


public class MainActivity extends BaseDrawerActivity implements FeedAdapter.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener {
    InterstitialAd interstitialAd;
    private Snackbar snackBar;
        public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    private boolean InternetConnection = true;
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;
    private static final String eventsUrl = "http://www.feather-techsolution.com/schol/events.php";
    private List<FeedAdapter.FeedItem> eventView;
    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;

    @BindView(R.id.content)
    CoordinatorLayout clContent;

    private FeedAdapter feedAdapter;

    private boolean pendingIntroAnimation;
ProgressView circularProgeress;
  private  AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId("ca-app-pub-8130545977288515/2189991184");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        interstitialAd.loadAd(adRequest);
//        interstitialAd.setAdListener(new AdListener() {
//
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                showadd();
//            }
//        });

        setupFeed();

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
//            feedAdapter.updateItems(false);
        }
        circularProgeress = (ProgressView) findViewById(R.id.circular_progress);

    }


    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        feedAdapter = new FeedAdapter(eventView, this);

//        rvFeed.setAdapter(feedAdapter);
        eventView = new ArrayList<>();
        getData();
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {

        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400)
        .start();
    }


    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }




    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }



    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }
    public void showUnLikedSnackbar() {
        Snackbar.make(clContent, "UnLiked!", Snackbar.LENGTH_SHORT).show();
    }
    private void getData(){

        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest(eventsUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                   circularProgeress.stop();
                        //calling method to parse json array
                        parseData(response);

//                   listData.add(getDataAdapter);
//                        adapter = new RecyclerViewImager(listData, this);

                        //Adding adapter to recyclerview
//                        recyclerView.setAdapter(adapter);
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar snackbar = Snackbar
                                .make(clContent, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });

// Changing message text color
                        snackbar.setActionTextColor(Color.WHITE);

// Changing snackbar background color
                        ViewGroup group = (ViewGroup) snackbar.getView();
                        group.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.btn_default_shadow));

// Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);

                        snackbar.show();

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonarrayRequest);
    }

    //
//    //This method will parse json data
    private void parseData(JSONArray array){
        for(int i = 0; i < array.length(); i++) {
            FeedAdapter.FeedItem geteventAdapter = new FeedAdapter.FeedItem();
            JSONObject json = null;
            try {

                json = array.getJSONObject(i);
//                geteventAdapter.setImageTitle(json.getString("title"));
                geteventAdapter.setImage(json.getString("image"));
                geteventAdapter.setTitle(json.getString("title"));



            } catch (JSONException e) {
                Snackbar snackbar = Snackbar
                        .make(clContent, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.setActionTextColor(Color.WHITE);

// Changing snackbar background color
                ViewGroup group = (ViewGroup) snackbar.getView();
                group.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.btn_default_shadow));

// Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                snackbar.show();

                feedAdapter.notifyDataSetChanged();

            }

            eventView.add(geteventAdapter);

        }

//        swipeRefreshLayout.setRefreshing(false);
        //Finally initializing our adapter
        feedAdapter = new FeedAdapter(eventView, MainActivity.this);
        feedAdapter.setOnFeedItemClickListener(this);
        //Adding adapter to recyclerview
       rvFeed.setAdapter(feedAdapter);


//        progressBar.setVisibility(View.GONE);
    }

    private void showadd() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}