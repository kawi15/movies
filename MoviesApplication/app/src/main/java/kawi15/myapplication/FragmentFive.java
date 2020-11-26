package kawi15.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kawi15.myapplication.database.DatabaseViewModel;
import kawi15.myapplication.database.Recomendation;
import kawi15.myapplication.database.Watched;

public class FragmentFive extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseViewModel databaseViewModel;
    private static RecyclerView recyclerView;
    private List<Recomendation> data;

    public static FragmentFive newInstance() {
        FragmentFive fragment = new FragmentFive();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_five, container, false);

        recyclerView = (RecyclerView) returnView.findViewById(R.id.fragment_five);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = databaseViewModel.getRecomendationList();
        adapter = new RecomendationAdapter(data);
        ((RecomendationAdapter) adapter).setOnRecomendationMovieClicked(movie -> {
            Intent intent = new Intent(getActivity(), MovieDetails.class);
            intent.putExtra("class", "watched");
            intent.putExtra("object", movie);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        return returnView;
    }
}
