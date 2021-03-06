//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.sunhydraulics.machine.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sunhydraulics.machine.R.layout;
import com.sunhydraulics.machine.model.Photo;
import com.sunhydraulics.machine.view.ProgressablePhotoView;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class WeekSingleImageFragment_
    extends com.sunhydraulics.machine.fragment.WeekSingleImageFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    public final static String ONLY_DOWNLOAD_ARG = "only_download";
    public final static String PHOTO_ARG = "photo";
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
            contentView_ = inflater.inflate(layout.fragment_single_image, container, false);
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
        injectFragmentArguments_();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static WeekSingleImageFragment_.FragmentBuilder_ builder() {
        return new WeekSingleImageFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        photoView = ((ProgressablePhotoView) hasViews.findViewById(com.sunhydraulics.machine.R.id.single_image));
        netError = hasViews.findViewById(com.sunhydraulics.machine.R.id.single_net_error);
        {
            View view = hasViews.findViewById(com.sunhydraulics.machine.R.id.single_content);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        WeekSingleImageFragment_.this.onPageClicked();
                    }

                }
                );
            }
        }
        init();
    }

    private void injectFragmentArguments_() {
        Bundle args_ = getArguments();
        if (args_!= null) {
            if (args_.containsKey(ONLY_DOWNLOAD_ARG)) {
                onlyDownload = args_.getBoolean(ONLY_DOWNLOAD_ARG);
            }
            if (args_.containsKey(PHOTO_ARG)) {
                photo = args_.getParcelable(PHOTO_ARG);
            }
        }
    }

    @Override
    public void toast(final int resId) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                WeekSingleImageFragment_.super.toast(resId);
            }

        }
        );
    }

    @Override
    public void setWallpaperOrDownload(final MenuItem item) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    WeekSingleImageFragment_.super.setWallpaperOrDownload(item);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<WeekSingleImageFragment_.FragmentBuilder_, com.sunhydraulics.machine.fragment.WeekSingleImageFragment>
    {


        @Override
        public com.sunhydraulics.machine.fragment.WeekSingleImageFragment build() {
            WeekSingleImageFragment_ fragment_ = new WeekSingleImageFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }

        public WeekSingleImageFragment_.FragmentBuilder_ onlyDownload(boolean onlyDownload) {
            args.putBoolean(ONLY_DOWNLOAD_ARG, onlyDownload);
            return this;
        }

        public WeekSingleImageFragment_.FragmentBuilder_ photo(Photo photo) {
            args.putParcelable(PHOTO_ARG, photo);
            return this;
        }

    }

}
