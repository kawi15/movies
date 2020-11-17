package kawi15.myapplication;

import androidx.recyclerview.widget.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private List<MovieDb> dataSet;
    private OnMovieDbClicked onMovieDbClicked;


    public void setOnMovieDbClicked(OnMovieDbClicked onMovieDbClicked) {
        this.onMovieDbClicked = onMovieDbClicked;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.text1);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.text2);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();

                if (onMovieDbClicked != null && pos != RecyclerView.NO_POSITION) {
                    MovieDb movieDb = dataSet.get(pos);
                    onMovieDbClicked.movieDbClicked(movieDb);
                }
            });
        }
    }

    public CustomAdapter(List<MovieDb> data) {
        this.dataSet = data;
    }

    public interface OnMovieDbClicked {
        void movieDbClicked(MovieDb movieDb);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + dataSet.get(listPosition).getPosterPath()).into(imageView);

        textViewName.setText(dataSet.get(listPosition).getOriginalTitle());
        textViewVersion.setText(dataSet.get(listPosition).getReleaseDate());


    }


    @Override
    public int getItemCount() {
        if(dataSet == null){
            return 0;
        }
        return dataSet.size();
    }
}
