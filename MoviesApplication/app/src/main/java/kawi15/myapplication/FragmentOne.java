package kawi15.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class FragmentOne extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private List<MovieDb> data;
    static View.OnClickListener myOnClickListener;

    public static FragmentOne newInstance() {
        FragmentOne fragment = new FragmentOne();
        return fragment;
    }

    public class MovieTask extends AsyncTask<Void, Void, List<MovieDb>> {
        @Override
        protected List<MovieDb> doInBackground(Void... voids) {
            MovieResultsPage movies = new TmdbApi("f753872c7aa5c000e0f46a4ea6fc49b2").getMovies().getUpcoming("en-US", 1, "US");
            List<MovieDb> listMovies = movies.getResults();

            return listMovies;
        }

        @Override
        protected void onPostExecute(List<MovieDb> movieDb) {
            data = movieDb;
            adapter = new CustomAdapter(data);
            recyclerView.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            //fun();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) returnView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());  // ???
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        MovieTask mt = new MovieTask();
        mt.execute();

        //adapter = new CustomAdapter(data);
        //recyclerView.setAdapter(adapter);



        return returnView;
    }

    public void fun(){
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}
