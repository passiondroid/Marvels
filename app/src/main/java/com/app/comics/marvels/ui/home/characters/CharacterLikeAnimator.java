package com.app.comics.marvels.ui.home.characters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.List;

/**
 * Created by arif on 23/12/17.
 */

public class CharacterLikeAnimator extends DefaultItemAnimator {

    //Check https://robots.thoughtbot.com/android-interpolators-a-visual-guide
    //To know about interpolators
    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    /**
     * When an item is changed, ItemAnimator can decide whether it wants to re-use the same ViewHolder
     * for animations or RecyclerView should create a copy of the item and ItemAnimator will use both
     * to run the animation (e.g. If the payload list is not empty, DefaultItemAnimator returns true.
     * @param viewHolder
     * @return
     */
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    /**
     * Called by the RecyclerView before the layout begins. Item animator should record necessary information
     * about the View before it is potentially rebound, moved or removed.
     * The data returned from this method will be passed to the related animate** methods.
     * @param state
     * @param viewHolder
     * @param changeFlags
     * @param payloads
     * @return
     */
    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int changeFlags, @NonNull List<Object> payloads) {
        if (changeFlags == FLAG_CHANGED) {
            for (Object payload : payloads) {
                if (payload instanceof String) {
                    return new CharacterItemHolderInfo((String) payload);
                }
            }
        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    /**
     * Called by the RecyclerView when an adapter item is present both before and after the layout and RecyclerView
     * has received a notifyItemChanged(int) call for it. This method may also be called when notifyDataSetChanged()
     * is called and adapter has stable ids so that RecyclerView could still rebind views to the same ViewHolders.
     */
    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {

        if (preInfo instanceof CharacterItemHolderInfo) {
            CharacterItemHolderInfo recipesItemHolderInfo = (CharacterItemHolderInfo) preInfo;
            CharacterRVAdapter.CharacterViewHolder holder = (CharacterRVAdapter.CharacterViewHolder) newHolder;

            if (CharacterRVAdapter.ACTION_LIKE_IMAGE_DOUBLE_CLICKED.equals(recipesItemHolderInfo.updateAction)) {
                animatePhotoLike(holder);
            }
        }
        return false;
    }

    private void animatePhotoLike(final CharacterRVAdapter.CharacterViewHolder holder) {
        holder.likeIV.setVisibility(View.VISIBLE);

        holder.likeIV.setScaleY(0.0f);
        holder.likeIV.setScaleX(0.0f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleLikeIcon = ObjectAnimator.ofPropertyValuesHolder(holder.likeIV, PropertyValuesHolder.ofFloat("scaleX", 0.0f, 2.0f), PropertyValuesHolder.ofFloat("scaleY", 0.0f, 2.0f), PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f, 0.0f));
        scaleLikeIcon.setInterpolator(DECELERATE_INTERPOLATOR);
        scaleLikeIcon.setDuration(1000);

        ObjectAnimator scaleLikeBackground = ObjectAnimator.ofPropertyValuesHolder(holder.characterCV, PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.95f, 1.0f), PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.95f, 1.0f));
        scaleLikeBackground.setInterpolator(DECELERATE_INTERPOLATOR);
        scaleLikeBackground.setDuration(600);

        animatorSet.playTogether(scaleLikeIcon, scaleLikeBackground);

        animatorSet.start();
    }

    public static class CharacterItemHolderInfo extends ItemHolderInfo {
        public String updateAction;

        public CharacterItemHolderInfo(String updateAction) {
            this.updateAction = updateAction;
        }
    }
}
