package com.app.blockchain.certnetwork.appcommon.base.adapter.animation;


import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;

/**
 * Created by「 The Khaeng 」on 28 Mar 2018 :)
 */
public interface AnimateViewHolderInterface {

    void preAnimateAddImpl(final RecyclerView.ViewHolder holder);

    void preAnimateRemoveImpl(final RecyclerView.ViewHolder holder);

    void animateAddImpl(final RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener);

    void animateRemoveImpl(final RecyclerView.ViewHolder holder,
                           ViewPropertyAnimatorListener listener);
}
