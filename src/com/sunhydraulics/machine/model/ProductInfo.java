package com.sunhydraulics.machine.model;

import com.lidroid.xutils.db.annotation.Column;

public class ProductInfo extends EntityBase {

	@Override
	public String toString() {
		return "ProductInfo [name=" + name + ", desc=" + desc + "]";
	}

	@Column(column = "name")
	private String name;

	@Column(column = "descinfo")
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
