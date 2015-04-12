package com.sunhydraulics.machine.utils;

import java.util.ArrayList;

public class MyArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156387397949769842L;

	public E getlastOnject() {
		if (this == null || this.size() == 0)
			return null;

		return this.get(this.size() - 1);
	}

}
