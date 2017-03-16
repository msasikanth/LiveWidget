package com.primudesigns.livewidget.ui.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.ui.adapters.FavoritesAdapter;
import com.primudesigns.livewidget.data.EventContract;
import com.primudesigns.livewidget.utils.Utils;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ProgressBar loading;
    private RecyclerView recyclerView;
    private final int LOADER = 15;

    private FavoritesAdapter adapter;

    //TODO 3 : NEED TO IMPROVE THE INITIAL FAVORITES TAB WITH FAV CHECK

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        getActivity().getSupportLoaderManager().initLoader(LOADER, null, this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_favorites_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        loading = (ProgressBar) view.findViewById(R.id.pb_loading);


        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<JSONObject> runtimeLoader = loaderManager.getLoader(LOADER);

        if (runtimeLoader == null) {
            loaderManager.initLoader(LOADER, null, this);
        } else {
            loaderManager.restartLoader(LOADER, null, this);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                String id = adapter.getID(viewHolder.getAdapterPosition());

                getActivity().getContentResolver().delete(EventContract.EventEntry.makeUriForStock(id), null, null);
                Utils.showSnackBar(getActivity(), R.id.container, "Item Removed", Snackbar.LENGTH_LONG);
                onResume();

            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getActivity()) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public Cursor loadInBackground() {
                return query();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d("status", "loadFinished");

        if (data != null && recyclerView != null) {
            loading.setVisibility(View.GONE);
            adapter = new FavoritesAdapter(getActivity(), data);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private Cursor query() {

        return getActivity().getContentResolver().query(EventContract.EventEntry.CONTENT_URI,
                null,
                null,
                null,
                EventContract.EventEntry._ID);

    }

    @Override
    public void onResume() {
        super.onResume();


        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<JSONObject> runtimeLoader = loaderManager.getLoader(LOADER);

        if (runtimeLoader == null) {
            loaderManager.initLoader(LOADER, null, this);
        } else {
            loaderManager.restartLoader(LOADER, null, this);
        }

    }
}
