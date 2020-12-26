package kawi15.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import kawi15.myapplication.database.DatabaseViewModel;
import kawi15.myapplication.database.Watchlist;

import static android.widget.Toast.LENGTH_SHORT;

public class FragmentTwo extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseViewModel databaseViewModel;
    private static RecyclerView recyclerView;
    private List<Watchlist> data;

    public static FragmentTwo newInstance() {
        FragmentTwo fragment = new FragmentTwo();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        data = databaseViewModel.getWatchlistList();
        adapter = new WatchlistAdapter(data);
        ((WatchlistAdapter) adapter).setOnWatchlistMovieClicked(movie -> {
            //databaseViewModel.deleteWatchlistMovie(movie);
            //Toast.makeText(getActivity(), "usunieto", LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MovieDetails.class);
            intent.putExtra("class", "watchlist");
            intent.putExtra("object", movie);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_two, container, false);

        recyclerView = (RecyclerView) returnView.findViewById(R.id.fragment_two);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        

        return returnView;


    }
}
