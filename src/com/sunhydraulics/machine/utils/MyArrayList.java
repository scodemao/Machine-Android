package com.sunhydraulics.machine.utils;

import java.util.ArrayList;

public class MyArrayList<E> extends ArrayList<E> {

	public E getlastOnject() {
		if (this == null || this.size() == 0)
			return null;

		return this.get(this.size() - 1);
	}

}
