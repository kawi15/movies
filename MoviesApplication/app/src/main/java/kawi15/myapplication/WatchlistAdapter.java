package kawi15.myapplication;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kawi15.myapplication.database.Watchlist;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {

    private OnWatchlistMovieClicked onWatchlistMovieClicked;
    private List<Watchlist> dataSet;

    public void setOnWatchlistMovieClicked(OnWatchlistMovieClicked onWatchlistMovieClicked){
        this.onWatchlistMovieClicked = onWatchlistMovieClicked;
    }

    public class WatchlistViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        TextView text2;
        ImageView imageView;

        public WatchlistViewHolder(View itemView) {
            super(itemView);
            this.text1 = (TextView) itemView.findViewById(R.id.watchlist_text1);
            this.text2 = (TextView) itemView.findViewById(R.id.watchlist_text2);
            this.imageView = (ImageView) itemView.findViewById(R.id.watchlist_image_view);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();

                if(onWatchlistMovieClicked != null && pos != RecyclerView.NO_POSITION){
                    Watchlist watchlist = dataSet.get(pos);
                    onWatchlistMovieClicked.watchlistMovieClicked(watchlist);
                }
            });
        }
    }

    public WatchlistAdapter(List<Watchlist> data){
        this.dataSet = data;
    }

    public interface OnWatchlistMovieClicked{
        void watchlistMovieClicked(Watchlist watchlist);
    }


    @Override
    public WatchlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watchlist_cards_layout, parent, false);


        WatchlistViewHolder myViewHolder = new WatchlistViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final WatchlistViewHolder holder, final int position) {
        TextView text1 = holder.text1;
        TextView text2 = holder.text2;
        ImageView imageView = holder.imageView;
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + dataSet.get(position).getPosterPath()).into(imageView);

        text1.setText(dataSet.get(position).getMovieTitle());
        text2.setText(dataSet.get(position).getReleaseDate());

    }

    @Override
    public int getItemCount() {
        if(dataSet == null){
            return 0;
        }
        return dataSet.size();
    }


}
