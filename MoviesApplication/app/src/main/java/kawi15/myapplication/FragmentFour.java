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
import kawi15.myapplication.database.Watched;
import kawi15.myapplication.database.Watchlist;

public class FragmentFour extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseViewModel databaseViewModel;
    private static RecyclerView recyclerView;
    private List<Watched> data;

    public static FragmentFour newInstance() {
        FragmentFour fragment = new FragmentFour();
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
        View returnView = inflater.inflate(R.layout.fragment_four, container, false);

        recyclerView = (RecyclerView) returnView.findViewById(R.id.fragment_four);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = databaseViewModel.getWatchedList();
        adapter = new WatchedAdapter(data);
        ((WatchedAdapter) adapter).setOnWatchedMovieClicked(movie -> {
            Intent intent = new Intent(getActivity(), MovieDetails.class);
            intent.putExtra("class", "watched");
            intent.putExtra("object", movie);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        return returnView;
    }
}
