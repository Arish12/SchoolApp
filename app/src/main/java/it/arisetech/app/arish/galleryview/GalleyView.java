//package it.arisetech.app.arish.galleryview;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import it.arisetech.app.arish.R;
//import it.arisetech.app.arish.ui.activity.BaseDrawerActivity;
//
//import static android.support.v7.appcompat.R.styleable.View;
//
//public class GalleyView extends BaseDrawerActivity {
//    private String TAG = "school";
//    private static final String endpoint = "http://www.feather-techsolution.com/schol/events.php";
//    private List<GalleyImage> eventView;
//    private ProgressDialog pDialog;
//    private GridViewImageAdapter mAdapter;
//    private RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_galley_view);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_gallery);
//
//        pDialog = new ProgressDialog(this);
//       eventView = new ArrayList<>();
//        mAdapter = new GridViewImageAdapter(getApplicationContext(), eventView);
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);
//            recyclerView.addOnItemTouchListener(new GridViewImageAdapter.RecyclerTouchListener(getApplicationContext()
//                    ,recyclerView, new GridViewImageAdapter.ClickListener() {
//                @Override
//                public void onClick(android.view.View view, int position) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("images", (Serializable) eventView);
//                    bundle.putInt("position", position);
//                    Toast.makeText(getApplicationContext(),"click click",Toast.LENGTH_LONG).show();
//                 FragmentTransaction ftm = getSupportFragmentManager().beginTransaction();
//                    SliderDialogFragment newFragment = SliderDialogFragment.newInstance();
//                    newFragment.setArguments(bundle);
////                    newFragment.show(ftm,"");
////                    ImageViewDialogFragment dialogFragment = new ImageViewDialogFragment ();
////                    Bundle bundle = new Bundle();
////                    bundle.putString("link",moviesList.get(position).getImage());
////                    dialogFragment.setArguments(bundle);
////                    dialogFragment.show((GalleryReviewActivity.this).getSupportFragmentManager(),"Image Dialog");
////                    newFragment.show(ft);
//
//                }
//
//                @Override
//                public void onLongClick(View view, int position) {
//
//                }
//            }));
//
//
////        recyclerView.addOnItemTouchListener(new GridViewImageAdapter().RecyclerTouchListener(getApplicationContext(), recyclerView, new GridViewImageAdapter().ClickListener() {
////            @Override
////            public void onClick(View view, int position) {
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("images", images);
////                bundle.putInt("position", position);
////
////                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                SliderDialogFragment newFragment = SliderDialogFragment.newInstance();
////                newFragment.setArguments(bundle);
////                newFragment.show(ft, "slideshow");
////            }
////
////            @Override
////            public void onLongClick(View view, int position) {
////
////            }
////        }));
//
//        fetchImages();
//    }
//
//    private void fetchImages() {
//
//        pDialog.setMessage("Wait...");
//        pDialog.show();
//
//        JsonArrayRequest req = new JsonArrayRequest(endpoint,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("+++++++", response.toString());
//                        pDialog.hide();
//
////                        images.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//
//                                JSONObject  object = response.getJSONObject(i);
//                                GalleyImage galleyImage = new GalleyImage();
//                                galleyImage.setImageUrl(object.getString("image"));
//
//                          eventView.add(galleyImage);
////
////                                json = array.getJSONObject(i);
//////                geteventAdapter.setImageTitle(json.getString("title"));
////                                geteventAdapter.setImage(json.getString("image"));
////                                geteventAdapter.setTitle(json.getString("title"));
////
//
//                            } catch (JSONException e) {
//                                Log.e("++++++++", "Json parsing error: " + e.getMessage());
//                            }
//                        }
//
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("++++++++", "Error: " + error.getMessage());
//                pDialog.hide();
//            }
//        });
//        //Creating request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        //Adding request to the queue
//        requestQueue.add(req);
//    }
//
//
//    }