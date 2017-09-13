package it.arisetech.app.arish.eventview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.util.List;

import it.arisetech.app.arish.R;
import it.arisetech.app.arish.ui.activity.ImageViewActivity;


/**
 * Created by Arish on 2/20/2017.
 */

public class EventsViewAdapter extends RecyclerView.Adapter<EventsViewAdapter.ViewHolder> {
    private Context context;
    private ImageLoader imageLoader;
    List<EventsView> eventsViews;

    public EventsViewAdapter(List<EventsView> eventData, Context context) {
        super();
        this.eventsViews = eventData;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_view_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       final EventsView eventView=  eventsViews.get(position);
        imageLoader = EventVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(eventView.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.prog,android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(eventView.getImageUrl(), imageLoader);
        holder.textViewName.setText(eventView.getImageTitle());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putExtra("image",eventView.getImageUrl());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsViews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView;
        public TextView textViewName;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.img_events_view);
            textViewName = (TextView) itemView.findViewById(R.id.img_title_view);

        }
    }
}
