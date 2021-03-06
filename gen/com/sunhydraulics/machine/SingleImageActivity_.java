//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.sunhydraulics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.sunhydraulics.machine.R.id;
import com.sunhydraulics.machine.R.layout;
import com.sunhydraulics.machine.model.Photo;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SingleImageActivity_
    extends SingleImageActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    public final static String ONLY_DOWNLOAD_EXTRA = "only_download";
    public final static String CURRENT_POSITION_EXTRA = "current_position";
    public final static String PHOTOS_EXTRA = "photos";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.act_single_image);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        injectExtras_();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static SingleImageActivity_.IntentBuilder_ intent(Context context) {
        return new SingleImageActivity_.IntentBuilder_(context);
    }

    public static SingleImageActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new SingleImageActivity_.IntentBuilder_(fragment);
    }

    public static SingleImageActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new SingleImageActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        viewpager = ((ViewPager) hasViews.findViewById(id.single_image_viewpager));
        init();
    }

    private void injectExtras_() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(ONLY_DOWNLOAD_EXTRA)) {
                onlyDownload = extras_.getBoolean(ONLY_DOWNLOAD_EXTRA);
            }
            if (extras_.containsKey(CURRENT_POSITION_EXTRA)) {
                currentPosition = extras_.getInt(CURRENT_POSITION_EXTRA);
            }
            if (extras_.containsKey(PHOTOS_EXTRA)) {
                photos = extras_.getParcelableArrayList(PHOTOS_EXTRA);
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<SingleImageActivity_.IntentBuilder_>
    {

        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, SingleImageActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            super(fragment.getActivity(), SingleImageActivity_.class);
            fragment_ = fragment;
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            super(fragment.getActivity(), SingleImageActivity_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent, requestCode);
                } else {
                    super.startForResult(requestCode);
                }
            }
        }

        public SingleImageActivity_.IntentBuilder_ onlyDownload(boolean onlyDownload) {
            return super.extra(ONLY_DOWNLOAD_EXTRA, onlyDownload);
        }

        public SingleImageActivity_.IntentBuilder_ currentPosition(int currentPosition) {
            return super.extra(CURRENT_POSITION_EXTRA, currentPosition);
        }

        public SingleImageActivity_.IntentBuilder_ photos(ArrayList<Photo> photos) {
            return super.extra(PHOTOS_EXTRA, ((Serializable) photos));
        }

    }

}
