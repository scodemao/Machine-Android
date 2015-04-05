package com.sunhydraulics.machine.preferences;

import org.androidannotations.annotations.sharedpreferences.SharedPref;
import org.androidannotations.annotations.sharedpreferences.SharedPref.Scope;

@SharedPref(value = Scope.UNIQUE)
public interface MyPref {
	boolean isLoadDetailDataFinished();

}
