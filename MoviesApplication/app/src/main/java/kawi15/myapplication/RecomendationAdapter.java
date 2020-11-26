package kawi15.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kawi15.myapplication.database.Recomendation;

public class RecomendationAdapter extends RecyclerView.Adapter<RecomendationAdapter.RecomendationViewHolder>{

    private RecomendationAdapter.OnRecomendationMovieClicked onRecomendationMovieClicked;
    private List<Recomendation> dataSet;

    public void setOnRecomendationMovieClicked(RecomendationAdapter.OnRecomendationMovieClicked onRecomendationMovieClicked){
        this.onRecomendationMovieClicked = onRecomendationMovieClicked;
    }


    public class RecomendationViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        TextView text2;
        ImageView imageView;

        public RecomendationViewHolder(View itemView) {
            super(itemView);
            this.text1 = (TextView) itemView.findViewById(R.id.recomendation_text1);
            this.text2 = (TextView) itemView.findViewById(R.id.recomendation_text2);
            this.imageView = (ImageView) itemView.findViewById(R.id.recomendation_image_view);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();

                if(onRecomendationMovieClicked != null && pos != RecyclerView.NO_POSITION){
                    Recomendation recomendation = dataSet.get(pos);
                    onRecomendationMovieClicked.recomendationMovieClicked(recomendation);
                }
            });

        }
    }

    public RecomendationAdapter(List<Recomendation> data){
        this.dataSet = data;
    }

    public interface OnRecomendationMovieClicked{
        void recomendationMovieClicked(Recomendation recomendation);
    }


    @Override
    public RecomendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recomendation_cards_layout, parent, false);


        RecomendationViewHolder myViewHolder = new RecomendationViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecomendationViewHolder holder, final int position) {
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
