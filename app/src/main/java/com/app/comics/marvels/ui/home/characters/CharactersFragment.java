package com.app.comics.marvels.ui.home.characters;

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

public class CharactersFragment extends Fragment{

    private RecyclerView characterRV;
    private ProgressBar progressBar;
    private CharacterRVAdapter adapter;
    private List<Result> resultList = new ArrayList<>();

    public static CharactersFragment newInstance() {

        Bundle args = new Bundle();
        CharactersFragment fragment = new CharactersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_characters, container, false);
        setUp(view);
        getCharacters();
        return view;
    }

    private void setUp(View view) {
        characterRV = view.findViewById(R.id.characterRV);
        progressBar = view.findViewById(R.id.progressBar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        characterRV.addItemDecoration(new CharacterItemDecoration(50));
        characterRV.setHasFixedSize(true);
        adapter = new CharacterRVAdapter(resultList);
        characterRV.setLayoutManager(gridLayoutManager);
        characterRV.setItemAnimator(new CharacterLikeAnimator());
        characterRV.setAdapter(adapter);
    }

    private void getCharacters() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String timeStamp = new Date().getTime()+"";
        String md5 = md5(timeStamp+Constants.PRIVATE_KEY+Constants.API_KEY);
        MarvelApi marvelApi = retrofit.create(MarvelApi.class);
        Call<Characters> call = marvelApi.getCharacters(timeStamp, Constants.API_KEY,md5,"80");
        call.enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                progressBar.setVisibility(View.GONE);
                resultList.addAll(response.body().getData().getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
