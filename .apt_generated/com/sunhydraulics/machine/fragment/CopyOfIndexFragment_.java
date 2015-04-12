//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.sunhydraulics.machine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.sunhydraulics.machine.R.layout;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

@SuppressLint({
    "HandlerLeak"
})
public final class CopyOfIndexFragment_
    extends com.sunhydraulics.machine.fragment.CopyOfIndexFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.layout_gridview, container, false);
        }
        return contentView_;
    }

    @Override
    public void onDestroyView() {
        contentView_ = null;
        super.onDestroyView();
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static CopyOfIndexFragment_.FragmentBuilder_ builder() {
        return new CopyOfIndexFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        gv_gridview = ((GridView) hasViews.findViewById(com.sunhydraulics.machine.R.id.gv_gridview));
        init();
    }

    @Override
    public void showInfo(final String msg) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                CopyOfIndexFragment_.super.showInfo(msg);
            }

        }
        );
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<CopyOfIndexFragment_.FragmentBuilder_, com.sunhydraulics.machine.fragment.CopyOfIndexFragment>
    {


        @Override
        public com.sunhydraulics.machine.fragment.CopyOfIndexFragment build() {
            CopyOfIndexFragment_ fragment_ = new CopyOfIndexFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }

    }

}
