package com.example.tryapp.object;

public class Executive extends Employee{

	private int percent;
	private int type;
	
	public Executive(String name, String eadd, String cellnum, String birthday, double income) {
		super(name, eadd, cellnum, birthday, income);
	}

	public Executive(String name, String eadd, String cellnum, String birthday, double income, int percent, int type) {
		super(name, eadd, cellnum, birthday, income);
		this.percent = percent;
		this.type = type;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public int getType() {
		return type;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
}
