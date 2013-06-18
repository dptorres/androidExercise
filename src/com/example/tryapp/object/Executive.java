package com.example.tryapp.object;

public class Executive extends Employee{

	private int percent;
	
	public Executive(String name, String eadd, String cellnum, String birthday, double income, int type) {
		super(name, eadd, cellnum, birthday, income, type);
	}

	public Executive(String name, String eadd, String cellnum, String birthday, double income, int type, int percent) {
		super(name, eadd, cellnum, birthday, income, type);
		this.percent = percent;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
}
