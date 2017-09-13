package it.arisetech.app.arish.homeview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.arisetech.app.arish.R;
import it.arisetech.app.arish.eventview.EventVolleyRequest;
import it.arisetech.app.arish.eventview.EventsView;
import it.arisetech.app.arish.ui.activity.MainActivity;
import it.arisetech.app.arish.ui.view.LoadingFeedItemView;


public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";
    public static final String ACTION_UNLIKE_BUTTON_CLICKED= "action_unlike_button";

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;
     private ImageLoader imageLoader;
    private  List<FeedItem> feedItems = new ArrayList<>();
    private Context context;
    private OnFeedItemClickListener onFeedItemClickListener;

    private boolean showLoadingView = false;

    public FeedAdapter(List<FeedItem> eventView, Context context) {
        this.context = context;
        this.feedItems = eventView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DEFAULT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
            CellFeedViewHolder cellFeedViewHolder = new CellFeedViewHolder(view);
            setupClickableViews(view, cellFeedViewHolder);
            return cellFeedViewHolder;
        } else if (viewType == VIEW_TYPE_LOADER) {
            LoadingFeedItemView view = new LoadingFeedItemView(context);
            view.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            return new LoadingCellFeedViewHolder(view);
        }

        return null;
    }

    private void setupClickableViews(final View view, final CellFeedViewHolder cellFeedViewHolder) {
        cellFeedViewHolder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onCommentsClick(view, cellFeedViewHolder.getAdapterPosition());
            }
        });
//        cellFeedViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFeedItemClickListener.onMoreClick(v, cellFeedViewHolder.getAdapterPosition());
//            }
//        });
//        cellFeedViewHolder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
//                feedItems.get(adapterPosition).likesCount++
//                ;
//                notifyItemChanged(adapterPosition, ACTION_LIKE_IMAGE_CLICKED);
//                if (context instanceof MainActivity) {
//                    ((MainActivity) context).showLikedSnackbar();
//                }
//            }
//        });
        cellFeedViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                feedItems.get(adapterPosition).likesCount++;
                notifyItemChanged(adapterPosition, ACTION_LIKE_BUTTON_CLICKED);

                if (context instanceof MainActivity) {
                    ((MainActivity) context).showLikedSnackbar();
                    cellFeedViewHolder.btnUnLike.setVisibility(View.VISIBLE);
                    cellFeedViewHolder.btnLike.setVisibility(View.INVISIBLE);
                }
            }
        });
        cellFeedViewHolder.btnUnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                feedItems.get(adapterPosition).likesCount--;
//                notifyItemChanged(adapterPosition,ACTION_UNLIKE_BUTTON_CLICKED);
//                onFeedItemClickListener.onProfileClick(view);
                if (context instanceof MainActivity) {
                    ((MainActivity) context).showUnLikedSnackbar();
                    cellFeedViewHolder.btnLike.setVisibility(View.VISIBLE);
                    cellFeedViewHolder.btnUnLike.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellFeedViewHolder) viewHolder).bindView(feedItems.get(position));
        final FeedItem eventView=  feedItems.get(position);
        imageLoader = EventVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(eventView.getImage(), ImageLoader.getImageListener(((CellFeedViewHolder) viewHolder).ivFeedCenter, R.drawable.prog,android.R.drawable.ic_dialog_alert));

       ((CellFeedViewHolder) viewHolder).ivFeedCenter.setImageUrl(eventView.getImage(), imageLoader);
((CellFeedViewHolder) viewHolder).homeTitle.setText(eventView.getTitle());

        if (getItemViewType(position) == VIEW_TYPE_LOADER) {
            bindLoadingFeedItem((LoadingCellFeedViewHolder) viewHolder);
        }
    }

    private void bindLoadingFeedItem(final LoadingCellFeedViewHolder holder) {
        holder.loadingFeedItemView.setOnLoadingFinishedListener(new LoadingFeedItemView.OnLoadingFinishedListener() {
            @Override
            public void onLoadingFinished() {
                showLoadingView = false;
                notifyItemChanged(0);
            }
        });
        holder.loadingFeedItemView.startLoading();
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public void updateItems(boolean animated) {
//        feedItems.clear();
//        feedItems.addAll(Arrays.asList(
//                new FeedItem(33, false),
//                new FeedItem(1, false),
//                new FeedItem(223, false),
//                new FeedItem(2, false),
//                new FeedItem(6, false),
//                new FeedItem(8, false),
//                new FeedItem(99, false)
//        ));
        if (animated) {
            notifyItemRangeInserted(0, feedItems.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFeedCenter)
        NetworkImageView ivFeedCenter;
        @BindView(R.id.ivFeedBottom)
        ImageView ivFeedBottom;
        @BindView(R.id.home_title_view)
        TextView homeTitle;
        @BindView(R.id.btnComments)
        ImageButton btnComments;
        @BindView(R.id.btnLike)
        ImageButton btnLike;
        @BindView(R.id.btnUnLike)
        ImageButton btnUnLike;
        @BindView(R.id.btnMore)
        ImageButton btnMore;
        @BindView(R.id.vBgLike)
        View vBgLike;
        @BindView(R.id.ivLike)
        ImageView ivLike;
        @BindView(R.id.tsLikesCounter)
        TextSwitcher tsLikesCounter;
        @BindView(R.id.ivUserProfile)
        ImageView ivUserProfile;
        @BindView(R.id.vImageRoot)
        FrameLayout vImageRoot;

        FeedItem feedItem;

        public CellFeedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(FeedItem feedItem) {
            if(feedItem.isLiked) {
                this.feedItem = feedItem;
                int adapterPosition = getAdapterPosition();
                btnUnLike.setImageResource(feedItem.isLiked ? R.drawable.thumps_like : R.drawable.ic_heart_outline_grey);
                tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(R.plurals.likes_uncount
                        , feedItem.likesCount, feedItem.likesCount));

            }
            else {
                this.feedItem = feedItem;
                int adapterPosition = getAdapterPosition();
                btnLike.setImageResource(feedItem.isLiked ? R.drawable.thumps_like : R.drawable.ic_heart_outline_grey);
                tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
                        R.plurals.likes_count, feedItem.likesCount, feedItem.likesCount
                ));
            }
        }


        public FeedItem getFeedItem() {
            return feedItem;
        }
    }

    public static class LoadingCellFeedViewHolder extends CellFeedViewHolder {

        LoadingFeedItemView loadingFeedItemView;

        public LoadingCellFeedViewHolder(LoadingFeedItemView view) {
            super(view);
            this.loadingFeedItemView = view;
        }

        @Override
        public void bindView(FeedItem feedItem) {
            super.bindView(feedItem);
        }
    }

    public static class FeedItem {
        public String id;
        public int likesCount;
        public int unlikesCount;
        public boolean isLiked;
        public boolean isUnliked;
        private String image;
         private String title;

        public FeedItem() {

        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


        public FeedItem(int likesCount, boolean isLiked) {
            this.likesCount = likesCount;
            this.isLiked = isLiked;
        }
    }

    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position);

        void onMoreClick(View v, int position);

//        void onProfileClick(View v);
    }
}
