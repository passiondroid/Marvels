package com.app.comics.marvels.ui.home.characters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.comics.marvels.R;
import com.app.comics.marvels.data.network.model.Result;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by arif on 22/12/17.
 */

public class CharacterRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String ACTION_LIKE_IMAGE_DOUBLE_CLICKED = "action_like_image_button";
    private List<Result> results;
    private int tapCount = 0;
    public CharacterRVAdapter(List<Result> results) {
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.character_item_layout,parent,false);
        final CharacterViewHolder characterViewHolder = new CharacterViewHolder(view);

        characterViewHolder.characterCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Result result = getItem(characterViewHolder.getAdapterPosition());
                if (result != null) {
                    tapCount++;
                    if (tapCount == 2) {
                        tapCount = 0;
                        onDoubleClick(characterViewHolder.getAdapterPosition(), result);
                    }
                }
            }
        });

        return characterViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CharacterViewHolder characterViewHolder = (CharacterViewHolder)holder;
        String path = results.get(position).getThumbnail().getPath()+"."+results.get(position).getThumbnail().getExtension();
        Glide.with(characterViewHolder.itemView.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .crossFade()
                .into(characterViewHolder.characterIV);

        characterViewHolder.nameTV.setText(results.get(position).getName());

    }

    private void onDoubleClick(int position, Result result) {
        notifyItemChanged(position, ACTION_LIKE_IMAGE_DOUBLE_CLICKED);
        //TODO: create a callback to save result item in db as favorite
        //callback.onLikeRecipeClick(position, result);
    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public Result getItem(int position) {
        if (position != RecyclerView.NO_POSITION)
            return results.get(position);
        else
            return null;
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTV;
        public ImageView characterIV;
        public ImageView likeIV;
        public CardView characterCV;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            characterIV = itemView.findViewById(R.id.characterIV);
            likeIV = itemView.findViewById(R.id.likeIV);
            characterCV = itemView.findViewById(R.id.character_card);
        }
    }
}
