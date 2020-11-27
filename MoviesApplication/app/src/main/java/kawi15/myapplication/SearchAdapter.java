package kawi15.myapplication;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import kawi15.myapplication.database.Recomendation;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private SearchAdapter.OnSearchMovieClicked onSearchMovieClicked;
    private List<MovieDb> dataSet;

    public void setOnSearchMovieClicked(SearchAdapter.OnSearchMovieClicked onSearchMovieClicked){
        this.onSearchMovieClicked = onSearchMovieClicked;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        TextView text2;
        ImageView imageView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            this.text1 = (TextView) itemView.findViewById(R.id.search_text1);
            this.text2 = (TextView) itemView.findViewById(R.id.search_text2);
            this.imageView = (ImageView) itemView.findViewById(R.id.search_image_view);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();

                if(onSearchMovieClicked != null && pos != RecyclerView.NO_POSITION){
                    MovieDb movieDb = dataSet.get(pos);
                    onSearchMovieClicked.searchMovieClicked(movieDb);
                }
            });
        }
    }

    public SearchAdapter(List<MovieDb> data){
        this.dataSet = data;
    }

    public interface OnSearchMovieClicked{
        void searchMovieClicked(MovieDb movieDb);
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_cards_layout, parent, false);


        SearchViewHolder myViewHolder = new SearchViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
        TextView text1 = holder.text1;
        TextView text2 = holder.text2;
        ImageView imageView = holder.imageView;
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + dataSet.get(position).getPosterPath()).into(imageView);

        text1.setText(dataSet.get(position).getOriginalTitle());
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
