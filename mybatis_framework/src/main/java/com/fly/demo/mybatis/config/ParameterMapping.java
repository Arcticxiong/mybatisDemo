package com.fly.demo.mybatis.config;

public class ParameterMapping {

	//#{}中的属性名称
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParameterMapping(String name) {
		super();
		this.name = name;
	}
	
	
}
