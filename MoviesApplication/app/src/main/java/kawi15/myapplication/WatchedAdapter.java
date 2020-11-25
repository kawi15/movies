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

import kawi15.myapplication.database.Watched;
import kawi15.myapplication.database.Watchlist;

public class WatchedAdapter extends RecyclerView.Adapter<WatchedAdapter.WatchedViewHolder>{

    private WatchedAdapter.OnWatchedMovieClicked onWatchedMovieClicked;
    private List<Watched> dataSet;

    public void setOnWatchedMovieClicked(WatchedAdapter.OnWatchedMovieClicked onWatchedMovieClicked){
        this.onWatchedMovieClicked = onWatchedMovieClicked;
    }

    public class WatchedViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        TextView text2;
        ImageView imageView;


        public WatchedViewHolder(View itemView) {
            super(itemView);
            this.text1 = (TextView) itemView.findViewById(R.id.watched_text1);
            this.text2 = (TextView) itemView.findViewById(R.id.watched_text2);
            this.imageView = (ImageView) itemView.findViewById(R.id.watched_image_view);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();

                if(onWatchedMovieClicked != null && pos != RecyclerView.NO_POSITION){
                    Watched watched = dataSet.get(pos);
                    onWatchedMovieClicked.watchedMovieClicked(watched);
                }
            });
        }
    }

    public WatchedAdapter(List<Watched> data){
        this.dataSet = data;
    }

    public interface OnWatchedMovieClicked{
        void watchedMovieClicked(Watched watched);
    }

    @Override
    public WatchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watched_cards_layout, parent, false);


        WatchedViewHolder myViewHolder = new WatchedViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final WatchedViewHolder holder, final int position) {
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
