package kawi15.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kawi15.myapplication.database.Recommendation;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>{

    private RecommendationAdapter.OnRecommendationMovieClicked onRecommendationMovieClicked;
    private List<Recommendation> dataSet;

    public void setOnRecommendationMovieClicked(RecommendationAdapter.OnRecommendationMovieClicked onRecommendationMovieClicked){
        this.onRecommendationMovieClicked = onRecommendationMovieClicked;
    }


    public class RecommendationViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        TextView text2;
        ImageView imageView;

        public RecommendationViewHolder(View itemView) {
            super(itemView);
            this.text1 = (TextView) itemView.findViewById(R.id.recommendation_text1);
            this.text2 = (TextView) itemView.findViewById(R.id.recommendation_text2);
            this.imageView = (ImageView) itemView.findViewById(R.id.recommendation_image_view);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();

                if(onRecommendationMovieClicked != null && pos != RecyclerView.NO_POSITION){
                    Recommendation recommendation = dataSet.get(pos);
                    onRecommendationMovieClicked.recommendationMovieClicked(recommendation);
                }
            });

        }
    }

    public RecommendationAdapter(List<Recommendation> data){
        this.dataSet = data;
    }

    public interface OnRecommendationMovieClicked{
        void recommendationMovieClicked(Recommendation recommendation);
    }


    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendation_cards_layout, parent, false);


        RecommendationViewHolder myViewHolder = new RecommendationViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecommendationViewHolder holder, final int position) {
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
