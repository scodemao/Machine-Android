//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.sunhydraulics.machine.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import org.androidannotations.api.sharedpreferences.BooleanPrefEditorField;
import org.androidannotations.api.sharedpreferences.BooleanPrefField;
import org.androidannotations.api.sharedpreferences.EditorHelper;
import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;

public final class MyPref_
    extends SharedPreferencesHelper
{


    public MyPref_(Context context) {
        super(context.getSharedPreferences("MyPref", 0));
    }

    public MyPref_.MyPrefEditor_ edit() {
        return new MyPref_.MyPrefEditor_(getSharedPreferences());
    }

    public BooleanPrefField isLoadDetailDataFinished() {
        return booleanField("isLoadDetailDataFinished", false);
    }

    public BooleanPrefField isLoadIndexDataFinished() {
        return booleanField("isLoadIndexDataFinished", false);
    }

    public BooleanPrefField isLoadPicDataFinished() {
        return booleanField("isLoadPicDataFinished", false);
    }

    public final static class MyPrefEditor_
        extends EditorHelper<MyPref_.MyPrefEditor_>
    {


        MyPrefEditor_(SharedPreferences sharedPreferences) {
            super(sharedPreferences);
        }

        public BooleanPrefEditorField<MyPref_.MyPrefEditor_> isLoadDetailDataFinished() {
            return booleanField("isLoadDetailDataFinished");
        }

        public BooleanPrefEditorField<MyPref_.MyPrefEditor_> isLoadIndexDataFinished() {
            return booleanField("isLoadIndexDataFinished");
        }

        public BooleanPrefEditorField<MyPref_.MyPrefEditor_> isLoadPicDataFinished() {
            return booleanField("isLoadPicDataFinished");
        }

    }

}
