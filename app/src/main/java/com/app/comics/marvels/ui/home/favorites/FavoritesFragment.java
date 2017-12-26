package com.app.comics.marvels.ui.home.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.comics.marvels.BuildConfig;
import com.app.comics.marvels.R;
import com.app.comics.marvels.data.network.MarvelApi;
import com.app.comics.marvels.data.network.model.Characters;
import com.app.comics.marvels.data.network.model.Result;
import com.app.comics.marvels.ui.home.characters.CharacterItemDecoration;
import com.app.comics.marvels.ui.home.characters.CharacterLikeAnimator;
import com.app.comics.marvels.ui.home.characters.CharacterRVAdapter;
import com.app.comics.marvels.util.Constants;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arif on 22/12/17.
 */

public class FavoritesFragment extends Fragment{

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        return view;
    }


}
